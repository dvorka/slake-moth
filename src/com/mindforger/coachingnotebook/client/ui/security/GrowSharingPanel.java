package com.mindforger.coachingnotebook.client.ui.security;

import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.RiaUtilities;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;
import com.mindforger.coachingnotebook.shared.beans.PermissionBean;

public class GrowSharingPanel extends FlexTable {
	
	private final ListBox sharingOptions;
	private FlexTable permissionsList;
	
	private Button shareTopButton;

	private PermissionBean[] acl;
	
	private RiaContext ctx;
	private RiaMessages i18n;

	public GrowSharingPanel(final RiaContext ctx, final Button shareTopButton) {
		setStyleName("mf-growSharingPanel");
		setVisible(false);
		
		this.ctx=ctx;
		i18n=ctx.getI18n();
		this.shareTopButton=shareTopButton;
		
		HorizontalPanel sharingOptionsPanel=new HorizontalPanel();
		sharingOptionsPanel.setStyleName("mf-sharingOptionsPanel");
		HTML html=new HTML(i18n.youAreSharingThisGoalWith()+"&nbsp;");
		sharingOptionsPanel.add(html);
		
		sharingOptions = new ListBox();
		for (int i = 0; i < GrowBean.SHARING_OPTIONS_VALUES.length; i++) {
			sharingOptions.addItem(GrowBean.SHARING_OPTIONS_LABELS[i], GrowBean.SHARING_OPTIONS_VALUES[i]);			
		}
		sharingOptions.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				handleSharingOptionsChange(ctx);
			}
		});
		sharingOptionsPanel.add(sharingOptions);
		setWidget(0,0,sharingOptionsPanel);
		
		// here comes list of user names & their rights: show & load them lazily; OK/Cancel
		permissionsList = new FlexTable();
		permissionsList.setStyleName("mf-growPermissionsList");
		setWidget(1, 0, permissionsList);
		
		HorizontalPanel buttonsPanel=new HorizontalPanel();
		buttonsPanel.setStyleName("mf-growSharingPanelButtons");		
		Button doneButton=new Button(i18n.done(),new ClickHandler() {
			public void onClick(ClickEvent event) {
				shareTopButton.setText(i18n.sharedTo()+" "+sharingOptions.getItemText(sharingOptions.getSelectedIndex()));
				ctx.getGrowPanel().save(false);

				ctx.getStatusLine().showProgress(i18n.savingAclForThisGoal());
				String sharingOption=sharingOptions.getValue(sharingOptions.getSelectedIndex());
				if(GrowBean.SHARING_OPTION_VALUE_SELECTED_CONNECTIONS.equals(sharingOption)) {
					ctx.getService().setGrowAcl(ctx.getGrowPanel().getGrowId(), acl, new AsyncCallback<Void>() {
						public void onSuccess(Void result) {
							ctx.getStatusLine().hideStatus();
							GrowSharingPanel.this.setVisible(false);
						}
						public void onFailure(Throwable caught) {
							ctx.getRia().handleServiceError(caught);
						}
					});										
				} else {
					if(GrowBean.SHARING_OPTION_VALUE_EVERYBODY.equals(sharingOption) || 
							GrowBean.SHARING_OPTION_VALUE_NOBODY.equals(sharingOption) ) {
						ctx.getService().setGrowAcl(ctx.getGrowPanel().getGrowId(), new PermissionBean[0], new AsyncCallback<Void>() {
							public void onSuccess(Void result) {
								ctx.getStatusLine().hideStatus();
								GrowSharingPanel.this.setVisible(false);
							}
							public void onFailure(Throwable caught) {
								ctx.getRia().handleServiceError(caught);
							}
						});					
					} else {
						if(GrowBean.SHARING_OPTION_VALUE_ALL_CONNECTIONS.equals(sharingOption)) {
							ctx.getService().setGrowAclToAllConnections(ctx.getGrowPanel().getGrowId(), new AsyncCallback<Void>() {
								public void onSuccess(Void result) {
									ctx.getStatusLine().hideStatus();
									GrowSharingPanel.this.setVisible(false);
								}
								public void onFailure(Throwable caught) {
									ctx.getRia().handleServiceError(caught);
								}
							});												
						} else {
							GrowSharingPanel.this.setVisible(false);							
						}
					}
				}
			}
		});
		doneButton.setStyleName("mf-button");
		buttonsPanel.add(doneButton);
		Button cancelButton=new Button(i18n.cancel(),new ClickHandler() {
			public void onClick(ClickEvent event) {
				GrowSharingPanel.this.setVisible(false);
			}
		});
		cancelButton.setStyleName("mf-button");		
		buttonsPanel.add(cancelButton);
		setWidget(2, 0, buttonsPanel);
	}
	
	private FlexTable getPermissionsList() {
		return permissionsList;
	}

	public void refresh(String sharedTo) {
		setVisible(false);

		int index=0;
		if(sharedTo!=null) {
			int itemCount = sharingOptions.getItemCount();
			for (int i = 0; i < itemCount; i++) {
				if(sharedTo.equals(sharingOptions.getValue(i))) {
					index=i;
					break;
				}
			}
		}
		sharingOptions.setItemSelected(index, true);
		shareTopButton.setText(i18n.sharedTo()+" "+sharingOptions.getItemText(sharingOptions.getSelectedIndex()));
		handleSharingOptionsChange(ctx);
	}
	
	public String getSharedTo() {
		String result=sharingOptions.getValue(sharingOptions.getSelectedIndex());
		if(result==null) {
			result=GrowBean.SHARING_OPTION_VALUE_NOBODY;
		}
		return result;
	}

	private void handleSharingOptionsChange(final RiaContext ria) {
		if(GrowBean.SHARING_OPTION_VALUE_SELECTED_CONNECTIONS.equals(sharingOptions.getValue(sharingOptions.getSelectedIndex()))) {
			getPermissionsList().setVisible(true);
			
			ria.getStatusLine().showProgress(i18n.loadingAcl());
			ria.getService().getGrowAcl(ria.getGrowPanel().getGrowId(), new AsyncCallback<Map<String, PermissionBean>>() {
				public void onSuccess(Map<String, PermissionBean> map) {
					ria.getStatusLine().hideStatus();
					acl=map.values().toArray(new PermissionBean[map.size()]);
					renderAcl(acl);														
				}				
				public void onFailure(Throwable caught) {
					ria.getRia().handleServiceError(caught);
				}
			});										
		} else {
			getPermissionsList().setVisible(false);
		}
	}
	
	private void renderAcl(PermissionBean[] permissions) {
		getPermissionsList().setWidget(0, 0, new HTML(i18n.who()));
		getPermissionsList().setWidget(0, 1, new HTML(i18n.noAccess()));
		getPermissionsList().setWidget(0, 2, new HTML(i18n.canView()));
		//getPermissionsList().setWidget(0, 4, new HTML(i18n.canComment()));
		//getPermissionsList().setWidget(0, 5, new HTML(i18n.canWrite()));
		
		for (int i = 0; i < permissions.length; i++) {
			final int row = i+1;
			// photo
			HorizontalPanel horizontalPanel=new HorizontalPanel();
			horizontalPanel.setStyleName("mf-growPermissionListPhotoName");
			String htmlString="<img src='"+RiaUtilities.getGravatatarUrl(permissions[i].getUser())+"?s=25&d=identicon'>";
			HTML photoHtml = new HTML(htmlString);
			horizontalPanel.add(photoHtml);
			// nickname
			HTML html = new HTML(permissions[i].getUser().getNickname());
			html.setStyleName("mf-growPermissionListNickname");
			horizontalPanel.add(html);
			getPermissionsList().setWidget(row, 0, horizontalPanel);
			
			// no
			RadioButton radioButton=new RadioButton(permissions[i].getUser().getUserId());
			if(permissions[i].getPermission()==PermissionBean.PERMISSION_NO) {
				radioButton.setValue(true);
			} else {
				radioButton.setValue(false);
			}
			radioButton.addValueChangeHandler(new GrowSharingRadioHandler(permissions[i],PermissionBean.PERMISSION_NO));
			getPermissionsList().setWidget(row, 1, radioButton);
			
			// read
			radioButton=new RadioButton(permissions[i].getUser().getUserId());
			if(permissions[i].getPermission()==PermissionBean.PERMISSION_READ) {
				radioButton.setValue(true);
			} else {
				radioButton.setValue(false);
			}
			radioButton.addValueChangeHandler(new GrowSharingRadioHandler(permissions[i],PermissionBean.PERMISSION_READ));
			getPermissionsList().setWidget(row, 2, radioButton);
		}
	}
}

class GrowSharingRadioHandler implements ValueChangeHandler<Boolean> {
	
	private PermissionBean bean;
	private int permission;

	public GrowSharingRadioHandler(PermissionBean bean, int permission) {
		this.bean=bean;
		this.permission=permission;
	}
	
	public void onValueChange(ValueChangeEvent<Boolean> event) {
		if(Boolean.TRUE.equals(event.getValue())) {
			bean.setPermission(permission);
		}
	}									
}