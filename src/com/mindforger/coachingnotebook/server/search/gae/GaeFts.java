package com.mindforger.coachingnotebook.server.search.gae;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GetIndexesRequest;
import com.google.appengine.api.search.GetResponse;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchException;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;
import com.mindforger.coachingnotebook.server.Utils;
import com.mindforger.coachingnotebook.server.search.Fts;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeQuestionAnswerBean;
import com.mindforger.coachingnotebook.shared.MindForgerResourceType;
import com.mindforger.coachingnotebook.shared.beans.DescriptorBean;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;

 /*
	Simple, single term query     foo               finds documents that have foo anywhere in them
	Phrase query                  "foo bar"    		finds documents with foo followed by bar
	Restricted queries            name:foo       	finds documents, with foo present in name field
	Negated queries               NOT foo        	finds all entities that do not have foo in them
	Conjunction of queries        foo bar         	finds all entities that have text foo and bar in them, though not necessary next to each other
	Disjunction of queries        foo OR bar   		returns all entries that have foo or bar or both
	Combination of above          foo OR name:bar "some phrase" NOT baz
 */
/**
 * Google App Engine fulltext search:
 * <ul>
 *   <li>Every user has its own PERSONAL index where are indexed all her/his data.
 *   <li>There is also GLOBAL index that is used to index data that are shared to ALL users.
 *   <li>Data that are shared just to specific set of users don't appear in their indices. It is rather feature as I don't want to
 *       mix other users data with my data.
 * </ul>
 */
public class GaeFts implements Fts {
	private static final Logger LOG=Logger.getLogger("GaeFTS");
	
	private static final String FIELD_NAME="name";
	private static final String FIELD_DESCRIPTION="description";
	private static final String FIELD_TYPE="type";
	private static final String FIELD_OWNER="owner";
	private static final String FIELD_GROW_KEY="growKey";
		
	public GaeFts() {
	}

	private Index getOrCreatePersonalIndex(String userId) {
		if(userId!=null) {
			return getIndex(INDEX_NAME_PERSONAL_PREFIX+userId);			
		}
		return null;
	}
	
	private Index getIndex(String indexName) {
		Index index = SearchServiceFactory.getSearchService().getIndex(IndexSpec.newBuilder().setName(indexName));
		return index;
	}

	@Override
	public DescriptorBean[] find(String query, String userId) {
		LOG.info("find("+query+")");
		Index index=getOrCreatePersonalIndex(userId);
		List<Document> documents = findDocuments(index, query, SEARCH_RESULTS_LIMIT);
		if(documents!=null && documents.size()>0) {
			LOG.info(" find("+query+") results: "+documents.size());
			DescriptorBean[] result=new DescriptorBean[documents.size()];
			for (int i = 0; i < documents.size(); i++) {
				result[i]=new DescriptorBean();
				Document document = documents.get(i);
				result[i].setId(document.getId());					
				Field type = document.getOnlyField(FIELD_TYPE);
				if(type!=null) {
					MindForgerResourceType realType = MindForgerResourceType.stringToType(type.getAtom());
					result[i].setType(realType);
					if(realType.equals(MindForgerResourceType.QUESTION)) {
						Field parentId =  document.getOnlyField(FIELD_GROW_KEY);
						if(parentId!=null) {
							result[i].setParentId(parentId.getAtom());				
						}						
					}
				}
				Field name = document.getOnlyField(FIELD_NAME);
				if(name!=null) {
					result[i].setName(name.getText());					
				}
				Field description = document.getOnlyField(FIELD_DESCRIPTION);
				if(description!=null) {
					result[i].setDescription(description.getText());					
				}
				LOG.info(result[i].toString());
			}
			return result;
		} else {
			return new DescriptorBean[0];
		}
	}	
		
	private List<Document> findDocumentsToDelete(String idFieldName, Index index, String growKey) {
		List<Document> matched = new ArrayList<Document>();
		try {
			Query query = Query.newBuilder().build(idFieldName+":\""+growKey+"\"");
			Results<ScoredDocument> response= index.search(query);
			Iterator<ScoredDocument> iterator = response.iterator();
			while(iterator.hasNext()) {
				try {
					index.deleteAsync(iterator.next().getId());					
				} catch(Exception ee) {
					LOG.log(Level.SEVERE, "Unable to delete "+idFieldName+" of grow with key"+growKey, ee);					
				}
			}
		} catch (SearchException e) {
		    if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
		        // retry
		    }
			LOG.log(Level.SEVERE, "Search exception "+idFieldName, e);
		} catch (RuntimeException e) {
			LOG.log(Level.SEVERE, "FTS delete grow questions request with failed - "+idFieldName, e);
		}
		return matched;		
	}
	
	private List<Document> findDocuments(Index index, String queryString, int limit) {
		List<Document> matched = new ArrayList<Document>();
		try {
			QueryOptions options=QueryOptions.newBuilder()
					.setLimit(limit)
					.build();
			Query query = Query.newBuilder().setOptions(options).build(queryString);
			Results<ScoredDocument> response= index.search(query);
			
			Iterator<ScoredDocument> iterator = response.iterator();
			while(iterator.hasNext()) {
				matched.add(iterator.next());
			}
		} catch (SearchException e) {
		    if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
		        // retry
		    }
			LOG.log(Level.SEVERE, "Search exception", e);
		} catch (RuntimeException e) {
			LOG.log(Level.SEVERE, "Search request with query " + queryString  + " and index "+index.getName()+" failed", e);
		}
		return matched;
	}	
	
	/*
	 * Security:
	 *  - userId ensures that other user indices will not be modified
	 */
	@Override
	public void add(GrowBean growBean, Locale locale, String userId) {
		String name = growBean.getName();
		String description = growBean.getDescription();

		Builder builder = Document.newBuilder();
		builder.setLocale(locale);
		builder.setId(growBean.getKey());
		builder.addField(Field.newBuilder().
				setName(FIELD_TYPE).
				setAtom(DOCUMENT_GROW));
		builder.addField(Field.newBuilder().
				setName(FIELD_OWNER).
				setText(userId));
		builder.addField(Field.newBuilder().
				setName(FIELD_NAME).
				setText((name==null?"":name)));
		builder.addField(Field.newBuilder().
				setName(FIELD_DESCRIPTION).
				setText((description==null?"":description)));
		
		Document document = builder.build();

		try {
			Index index=getOrCreatePersonalIndex(userId);
			index.putAsync(document);
			LOG.log(Level.INFO, "FTS indexing "+name);
		} catch (PutException e) {
			if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
				// retry putting the document
			}
			LOG.log(Level.SEVERE, "Search exception", e);
		} catch (RuntimeException e) {
			LOG.log(Level.SEVERE, "Unable to index GROW bean " + name, e);
		}		
	}

	
	@Override
	public void delete(MindForgerResourceType type, String key, String userId) {
		Index index=getOrCreatePersonalIndex(userId);
		
		switch(type) {
		case GROW:
			index.deleteAsync(key);
			findDocumentsToDelete(FIELD_GROW_KEY, index, key);
			break;
		case QUESTION:
			index.deleteAsync(key);
			break;
		}				
	}

	@Override
	public void add(List<GaeQuestionAnswerBean> questions, String growKey, Locale locale, String userId) {
		if(questions!=null && questions.size()>0) {
			List<Document> documents=new ArrayList<Document>();
			for (GaeQuestionAnswerBean bean : questions) {
				String name = bean.getQuestion();
				String description = (bean.getAnswer()==null?null:bean.getAnswer().getValue());				
				Builder builder = Document.newBuilder();
				builder.setId(Utils.keyToString(bean.getKey()));
				builder.addField(Field.newBuilder().
						setName(FIELD_TYPE).
						setAtom(DOCUMENT_QUESTION));
				builder.addField(Field.newBuilder().
						setName(FIELD_OWNER).
						setText(userId).
						setLocale(locale));
				builder.addField(Field.newBuilder().
						setName(FIELD_NAME).
						setText((name==null?"":name)));
				builder.addField(Field.newBuilder().
						setName(FIELD_DESCRIPTION).
						setText((description==null?"":description)));
				builder.addField(Field.newBuilder().
						setName(FIELD_GROW_KEY).
						setAtom(growKey));
				
				documents.add(builder.build());
			}
			
			try {
				Index index=getOrCreatePersonalIndex(userId);
				index.putAsync(documents);
				LOG.log(Level.INFO, "FTS indexing "+documents.size()+" questions");
			} catch (PutException e) {
			    if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
			        // retry
			    }
				LOG.log(Level.SEVERE, "FTS put exception", e);
			} catch (RuntimeException e) {
				LOG.log(Level.SEVERE, "Unable to index questions!", e);
			}		
		}
	}
	
	public String[] getIndexesNames() {
		List<String> result=new ArrayList<String>();
		GetResponse<Index> response = SearchServiceFactory.getSearchService().getIndexes(
			    GetIndexesRequest.newBuilder().build());
		List<Index> results = response.getResults();
		for(Index i : results) {
			result.add(i.getName());
		}
		return result.toArray(new String[result.size()]);
	}	
}
