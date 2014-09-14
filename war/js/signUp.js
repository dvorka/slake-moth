/*!
 * Landing and sign up pages JavaScript.
 */

function toggleTermsAndConditions() {
	if(document.getElementById('termsAndConditionsText').style.display=='block') {
		document.getElementById('termsAndConditionsText').style.display='none';
	} else {
		document.getElementById('termsAndConditionsText').style.display='block';
	}
}

function toggleAcceptButton() {
	if(document.getElementById('iHaveReadTerms').checked == true) {
		//alert("a");
		document.getElementById('createMyAccountAnchor').style.display='inline';
		document.getElementById('createMyAccountAnchorDisabled').style.display='none';
	} else {
		//alert("b");
		document.getElementById('createMyAccountAnchor').style.display='none';
		document.getElementById('createMyAccountAnchorDisabled').style.display='inline';	
	}
}

