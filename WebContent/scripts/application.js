function CheckBoxState(chk) {

	if (document.data.selectAll.checked) {
		for (i = 0; i < chk.length; i++)
			chk[i].checked = true;

		// For membership and Edit button
		if (chk.length == 1) {
			document.getElementById("edit").disabled = false;
			document.getElementById("membershipview").disabled = false;
		} else {
			document.getElementById("edit").disabled = true;
			document.getElementById("membershipview").disabled = true;
		}
		// For the Delete Button
		document.getElementById("delete").disabled = false;
	} else {
		for (i = 0; i < chk.length; i++)
			chk[i].checked = false;
		setFunctions();
	}

}

function CheckBoxStateMember(chk) {

	if (document.data.selectAll.checked) {
		for (i = 0; i < chk.length; i++)
			chk[i].checked = true;

		// For membership and Edit button
		if (chk.length == 1) {
			document.getElementById("edit").disabled = false;

		} else {
			document.getElementById("edit").disabled = true;

		}
		// For the Delete Button
		document.getElementById("delete").disabled = false;
	} else {
		for (i = 0; i < chk.length; i++)
			chk[i].checked = false;
		setFunctions();
	}

}

function CheckClick(chk) {

	var chkd = 0;

	if (chk.checked == true) {
		chkd = chkd + 1;

	} else {

		for (i = 0; i < chk.length; i++) {
			if (chk[i].checked == true) {
				chkd = chkd + 1;

			}

		}
	} // For Delete Button
	if (chkd < 1) {

		document.getElementById("delete").disabled = true;
	} else {
		document.getElementById("delete").disabled = false;
	}

	// For Edit And Member Ship Button
	if (chkd > 1 || chkd < 1) {
		document.getElementById("edit").disabled = true;
		document.getElementById("membershipview").disabled = true;

	} else {
		document.getElementById("edit").disabled = false;
		document.getElementById("membershipview").disabled = false;
	}

}

function CheckClickMember(chk) {

	var chkd = 0;
	

	if (chk.checked == true) {

		chkd = chkd + 1;
	} else {
		for (i = 0; i < chk.length; i++) {
			if (chk[i].checked == true) {
				chkd = chkd + 1;

			}

		}

	}
	// For Delete Button
	if (chkd < 1) {

		document.getElementById("delete").disabled = true;
	} else {
		document.getElementById("delete").disabled = false;
	}

	// For Edit And MemberShip Button
	if (chkd == 1) {
		document.getElementById("edit").disabled = false;
		document.getElementById("membershipview").disabled = false;

	} else {
		document.getElementById("edit").disabled = true;
		document.getElementById("membershipview").disabled = true;

	}

}

function setMemberIdSubmit(chk) {

	var s = "";
	var tabl = document.getElementById('memberdata');
	if (chk.checked == true) {

		var tr = tabl.rows[1];

		var cll = tr.cells[1];
		var id = cll.innerHTML;
		// alert(id);
		document.edit.memberId.value = id;

		document.edit.submit();

	} else {

		for (j = 0; j < chk.length; j++) {
			if (chk[j].checked == true) {

				var tr = tabl.rows[j + 1];

				var cll = tr.cells[1];
				var id = cll.innerHTML;
				document.edit.memberId.value = id;

				document.edit.submit();
			}

		}
	}
}

function setUserNameForEdit(chk) {

	var s = "";
	var tabl = document.getElementById('userdata');
	if (chk.checked == true) {

		var tr = tabl.rows[1];

		var cll = tr.cells[1];
		var id = cll.innerHTML;
		// alert(id);
		document.edit.username.value = id;

		document.edit.submit();

	} else {

		for (j = 0; j < chk.length; j++) {
			if (chk[j].checked == true) {

				var tr = tabl.rows[j + 1];

				var cll = tr.cells[1];
				var id = cll.innerHTML;
				document.edit.username.value = id;

				document.edit.submit();
			}

		}
	}
}


function setMemberIdSubmitIndividual(chk) {

	var s = "";
	var members = "";
	var chkd = 0;
	var id = "";
	var tabl = document.getElementById('memberdataIndividual');
	if (chk.length == 1 && chk.checked == true) {

		var tr = tabl.rows[1];

		var cll = tr.cells[1];
		var id = cll.innerHTML;
		// alert(id);
		document.individual.memberIdToSms.value = id;

		document.individual.submit();

	
	}else {

		if (chk.length > 1) {
			//alert("In else");
		for (j = 0; j < chk.length; j++) {
			if (chk[j].checked == true) {

				var tr = tabl.rows[j + 1];

				var cll = tr.cells[1];
				id = cll.innerHTML;
				if (chkd == 0) {
					members = id;
					//alert("ID "+id);
				} else {

					members = members + ":" + id;

				}
				chkd = chkd + 1;
				//alert("Members in loop "+members);
				// document.edit.memberIdToDelete.value = id;

			}

		}
		}
		
	}
	document.individual.memberIdToSms.value = members;
	//alert(document.birthday.memberIdToSms.value);
	document.individual.submit();
}

function setMemberIdSubmitBirthday(chk) {
	
	var s = "";
	var members = "";
	var chkd = 0;
	var id = "";
	var tabl = document.getElementById('memberdatabirthday');
	//alert(chk.length);
	
	if (chk.length == 1 && chk.checked == true) {

		var tr = tabl.rows[1];

		var cll = tr.cells[1];
		var id = cll.innerHTML;
		// alert(id);
		document.birthday.memberIdToSms.value = id;

		document.birthday.submit();

	
	}else {
		if (chk.length > 1) {
			//alert("In else");
		for (j = 0; j < chk.length; j++) {
			if (chk[j].checked == true) {

				var tr = tabl.rows[j + 1];

				var cll = tr.cells[1];
				id = cll.innerHTML;
				if (chkd == 0) {
					members = id;
					//alert("ID "+id);
				} else {

					members = members + ":" + id;

				}
				chkd = chkd + 1;
				//alert("Members in loop "+members);
				// document.edit.memberIdToDelete.value = id;

			}

		}
		}
		
	}
	document.birthday.memberIdToSms.value = members;
	//alert(document.birthday.memberIdToSms.value);
	document.birthday.submit();
	
	
}


function setSearchString(val) {

	document.search.stringToSearch.value = val;

	document.search.submit();

}

function setMemberIdToDelete(chk) {
	// alert("Working");

	var members = "";
	var chkd = 0;
	var id = "";

	var tabl = document.getElementById('memberdata');

	if (chk.checked == true) {

		var tr = tabl.rows[1];

		var cll = tr.cells[1];
		var id = cll.innerHTML;

		document.toDelete.memberIdToDelete.value = id;
		document.toDelete.submit();

	} else {

		if (chk.length > 1) {
			for (j = 0; j < chk.length; j++) {
				if (chk[j].checked == true) {

					var tr = tabl.rows[j + 1];

					var cll = tr.cells[1];
					id = cll.innerHTML;
					if (chkd == 0) {
						members = id;
						// alert("ID "+id);
					} else {

						members = members + ":" + id;

					}
					chkd = chkd + 1;
					// alert("Members in loop "+members);
					// document.edit.memberIdToDelete.value = id;

				}

			}
			document.toDelete.memberIdToDelete.value = members;
		}

		else {
			document.toDelete.memberIdToDelete.value = id;
		}
	}
	document.toDelete.submit();
}


function setMemberIds(chk) {
	// alert("Working");

	var members = "";
	var chkd = 0;
	var id = "";

	var tabl = document.getElementById('memberdata');

	if (chk.checked == true) {

		var tr = tabl.rows[1];

		var cll = tr.cells[1];
		var id = cll.innerHTML;

		document.toSms.memberIdToSms.value = id;
		document.toSms.submit();

	} else {

		if (chk.length > 1) {
			for (j = 0; j < chk.length; j++) {
				if (chk[j].checked == true) {

					var tr = tabl.rows[j + 1];

					var cll = tr.cells[1];
					id = cll.innerHTML;
					if (chkd == 0) {
						members = id;
						// alert("ID "+id);
					} else {

						members = members + ":" + id;

					}
					chkd = chkd + 1;
					// alert("Members in loop "+members);
					// document.edit.memberIdToDelete.value = id;

				}

			}
			document.toSms.memberIdToSms.value = members;
		}

		else {
			document.toSms.memberIdToSms.value = id;
		}
	}
	document.toSms.submit();
}

function setCellgroupToDelete(chk) {

	var cellgroups = "";
	var chkd = 0;
	var id = "";

	var tabl = document.getElementById('cellgroupdata');
	if (chk.length > 1) {
		for (j = 0; j < chk.length; j++) {
			if (chk[j].checked == true) {

				var tr = tabl.rows[j + 1];

				var cll = tr.cells[1];
				id = cll.innerHTML;
				if (chkd == 0) {
					cellgroups = id;

				} else {

					cellgroups = cellgroups + ":" + id;

				}
				chkd = chkd + 1;

				// document.edit.memberIdToDelete.value = id;

			}

		}
		document.toDelete.cellgroupToDelete.value = cellgroups;
	} else {
		document.toDelete.cellgroupToDelete.value = id;
	}
	// alert("Members to Delete"+cellgroups);
	document.toDelete.submit();
}

function setRoleToDelete(chk) {

	var roles = "";
	var chkd = 0;
	var id = "";

	var tabl = document.getElementById('rolesdata');
	if (chk.length > 1) {
		for (j = 0; j < chk.length; j++) {
			if (chk[j].checked == true) {

				var tr = tabl.rows[j + 1];

				var cll = tr.cells[1];
				id = cll.innerHTML;
				if (chkd == 0) {
					roles = id;

				} else {

					roles = roles + ":" + id;

				}
				chkd = chkd + 1;

				// document.edit.memberIdToDelete.value = id;

			}

		}
		document.toDelete.roleToDelete.value = roles;
	} else {
		document.toDelete.roleToDelete.value = id;
	}
	// alert("Members to Delete"+cellgroups);
	document.toDelete.submit();
}

function refreshPager(fromID) {
	var i = document.getElementById(fromID).selectedIndex;
	
	var val = document.getElementById(fromID).options[i].value;
	
	document.apply.pageSizeOption.value = val;
	//alert(document.apply.pageSizeOption.value);
	document.getElementById(fromID).options[i].selected = true;
	document.apply.submit();
	
}

function setCellgroupNameSubmit(chk) {

	var s = "";

	var tabl = document.getElementById('cellgroupdata');

	if (chk.checked == true) {
		var tr = tabl.rows[1];

		var cll = tr.cells[1];
		var id = cll.innerHTML;
		document.edit.cellgroupName.value = id;

		document.edit.submit();

	} else {
		for (j = 0; j < chk.length; j++) {
			if (chk[j].checked == true) {

				var tr = tabl.rows[j + 1];

				var cll = tr.cells[1];
				var id = cll.innerHTML;
				document.edit.cellgroupName.value = id;

				document.edit.submit();
			}

		}
	}
}

function setRoleNameSubmit(chk) {
	var s = "";
	// alert("workings eew");

	var tabl = document.getElementById('rolesdata');

	if (chk == true) {
		var tr = tabl.rows[1];

		var cll = tr.cells[1];
		var id = cll.innerHTML;
		document.edit.roleId.value = id;

		document.edit.submit();

	} else {
		for (j = 0; j < chk.length; j++) {
			if (chk[j].checked == true) {

				var tr = tabl.rows[j + 1];

				var cll = tr.cells[1];
				var id = cll.innerHTML;
				document.edit.roleId.value = id;

				document.edit.submit();
			}

		}
	}
}

function setRoleNameSubmitMembership(chk) {

	var s = "";
	
	if(chk.checked == true ){
		var tr = tabl.rows[1];

		var cll = tr.cells[1];
		var id = cll.innerHTML;
		document.membership.roleId.value = id;
		//alert(id+"ID Value"+document.membership.roleId.value);

		document.membership.submit();
		
	}
	else{
	var tabl = document.getElementById('rolesdata');
	for (j = 0; j < chk.length; j++) {
		if (chk[j].checked == true) {

			var tr = tabl.rows[j + 1];

			var cll = tr.cells[1];
			var id = cll.innerHTML;
			document.membership.roleId.value = id;
			// alert("ID Value"+document.membership.cellgroupNameMembers.value);

			document.membership.submit();
		}

	}
	}
}
function setCellgroupNameSubmitMembership(chk) {

	var s = "";

	var tabl = document.getElementById('cellgroupdata');
	for (j = 0; j < chk.length; j++) {
		if (chk[j].checked == true) {

			var tr = tabl.rows[j + 1];

			var cll = tr.cells[1];
			var id = cll.innerHTML;
			document.membership.cellgroupNameMembers.value = id;
			// alert("ID Value"+document.membership.cellgroupNameMembers.value);

			document.membership.submit();
		}

	}
}

function setFunctions() {

	if ((document.getElementById("edit") != null)
			&& (document.getElementById("delete") != null)) {
		document.getElementById("edit").disabled = true;
		document.getElementById("delete").disabled = true;
	}
	if (document.getElementById("membershipview") != null) {
		document.getElementById("membershipview").disabled = true;
	}

}

function moveOption(fromID, toID) {
	var i = document.getElementById(fromID).selectedIndex;
	var o = document.getElementById(fromID).options[i];
	var theOpt = new Option(o.text, o.value, false, false);
	// alert(o.value);

	document.getElementById(toID).options[document.getElementById(toID).options.length] = theOpt;
	document.getElementById(fromID).options[i] = null;

	if (o.value == 'all') {
		moveAll(fromID, toID);
	}

	if (toID == 'groupsAllocated') {

		selectAllToSend(toID, 'groupsToSend');

	} else {

		selectAllToSend(fromID, 'groupsToSend');
	}
}

function moveAll(fromID, toID) {

	// var i = document.getElementById( fromID ).selectedIndex;
	var fromlength = document.getElementById(fromID).options.length;
	// alert('Moving all'+fromlength);

	for (j = fromlength - 1; j >= 0; j--) {

		var o = document.getElementById(fromID).options[j];
		var theOpt = new Option(o.text, o.value, false, false);

		document.getElementById(toID).options[j + 1] = theOpt;
		document.getElementById(fromID).options[j] = null;

	}

}

function saveOptions(selectID) {
	var length = document.getElementById(toID).options.length;
	var string = "";
	for (j = 0; j < length; j++) {
		var o = document.getElementById(fromID).options[j]
		string = o.text + "," + string;
	}
	var theOpt = new Option(o.text, o.value, false, false);

	document.getElementById(toID).options[document.getElementById(toID).options.length] = theOpt;
	document.getElementById(fromID).options[i] = null;
}
function selectAll(selectID) {
	var length = document.getElementById(selectID).options.length;

	for (j = 0; j < length; j++) {
		var o = document.getElementById(selectID).options[j]
		document.getElementById(selectID).options[j].selected = true;
	}

}

function selectAllToSend(selectID, groupsToSend) {
	var length = document.getElementById(selectID).options.length;
	// var toSend = document.sendSMS_action.groupsToSend;
	var toSend = document.getElementById("groupsToSend");
	var allSelected = "00";
	for (j = 0; j < length; j++) {
		var o = document.getElementById(selectID).options[j];
		if (j == 0) {
			allSelected = o.value;
		} else {
			document.getElementById(selectID).options[j].selected = true;
			allSelected = document.getElementById(selectID).options[j].value
					+ "," + allSelected;
		}
	}

	toSend.value = allSelected;
	// alert(toSend.value+'work');

}

function tabview_aux(TabViewId, id) {
	var TabView = document.getElementById(TabViewId);

	var Tabs = TabView.firstChild;
	while (Tabs.className != "Tabs")
		Tabs = Tabs.nextSibling;

	var Tab = Tabs.firstChild;
	var i = 0;

	do {
		if (Tab.tagName == "A") {
			i++;
			Tab.href = "javascript:tabview_switch('" + TabViewId + "', " + i
					+ ");";
			Tab.className = (i == id) ? "Active" : "";
			Tab.blur();
		}
	} while (Tab = Tab.nextSibling);

	var Pages = TabView.firstChild;
	while (Pages.className != 'Pages')
		Pages = Pages.nextSibling;

	var Page = Pages.firstChild;
	var i = 0;

	do {
		if (Page.className == 'Page') {
			i++;
			if (Pages.offsetHeight)
				Page.style.height = (Pages.offsetHeight - 2) + "px";
			Page.style.overflow = "auto";
			Page.style.display = (i == id) ? 'block' : 'none';
		}
	} while (Page = Page.nextSibling);
}

function tabview_switch(TabViewId, id) {
	tabview_aux(TabViewId, id);
}

function tabview_initialize(TabViewId,id) {
	tabview_aux(TabViewId, id);
}

function CheckMaxLength(txt, divId) {
	
	var divObj = document.getElementById(divId);
	divObj.innerHTML = "You have typed " + (txt.value.length) + " characters!";

}
