function createCell(cell, style) {
    var div = document.createElement('div'), // create DIV element
        span = document.createElement('span'); // create span element
    div.appendChild(document.createTextNode('\u00A0'));                    // append text node to the DIV
    div.setAttribute('align', 'center');
    //div.setAttribute('contenteditable', 'true');
    
    span.setAttribute('class', style);    
    span.appendChild(div);
    cell.setAttribute('width', 'auto');
    cell.appendChild(span);                   // append DIV to the table cell
}
//*******************************************************************************************
function appendRow() {
    var tbl=getTable();
    var columnCount = tbl.rows[0].cells.length;
    var row = tbl.insertRow(tbl.rows.length);
    if ((tbl.rows.length-1)%4 == 0) style='style2';
    else style='style1';
    
    // For Every Row Added a Checkbox on first cell--------------------------------------
    var element_1 = document.createElement("input");
    element_1.type = "checkbox";
    row.insertCell(0).appendChild(element_1);
    
    for (var i = 1; i < columnCount; i++) 
        createCell(row.insertCell(i),style);
}
//*******************************************************************************************
function addRowBefore() {
    var tbl=getTable();
    for (var i = 1; i < tbl.rows.length; i++) {
        var chkbox = tbl.rows[i].cells[0].childNodes[0];
        if (null != chkbox && true == chkbox.checked) {
            var row = tbl.insertRow(i);
            if ((i-1)%4 == 0) style='style2';
            else style='style1';
            // For Every Row Added a Checkbox on first cell--------------------------------------
            var element_1 = document.createElement("input");
            element_1.type = "checkbox";
            row.insertCell(0).appendChild(element_1);
            
            // For Every Row Added add a textbox on the rest of the cells starting with the 3rd,4th,5th...  coloumns going on...
            for (var j = 1; j < tbl.rows[0].cells.length; j++) 
                createCell(row.insertCell(j),style);
            
            i++;
        }
    }
}
// ***************************************************************************************
function appendColumn() {        
    var tblBodyObj=getTable();   
    //for every Coloumn Added Add checkbox on first row ----------------------------------------------
    var element_4 = document.createElement("input");
    element_4.type = "checkbox";
    tblBodyObj.rows[0].insertCell(-1).appendChild(element_4);
   
    for (var i = 1; i < tblBodyObj.rows.length; i++) {
        if (i%4 == 0) style='style2';
            else style='style1';
        createCell(tblBodyObj.rows[i].insertCell(-1),style);
    }     
}
// ***************************************************************************************
function addColumnBefore() {
    var tb=getTable();
    var NoOfcolumns = tb.rows[0].cells.length;
    
    for (var clm = 1; clm < NoOfcolumns; clm++) {
        var rw = tb.rows[0];
        var chkbox = rw.cells[clm].childNodes[0];
        if (null != chkbox && true == chkbox.checked) {
            
            //for every Coloumn Added Add checkbox on first row ----------------------------------------------
            var newchkbxcell = tb.rows[0].insertCell(clm);
            var element_4 = document.createElement("input");
            element_4.type = "checkbox";
            element_4.setAttribute('id', 'newCheckbox');
            newchkbxcell.appendChild(element_4);
            
            for (var i = 1; i < tb.rows.length; i++) {
                if (i%4 == 0) style='style2';
            else style='style1';
                //alert(style);
                var newCell = tb.rows[i].insertCell(clm);
                createCell(newCell,style);
            }
            NoOfcolumns++;
            clm++;
        }
    }
}
//*******************************************************************************
function deleteSelectedRows() {
    var tb=getTable();
    var NoOfrows = tb.rows.length;
    for (var i = 0; i < NoOfrows; i++) {
        var row = tb.rows[i];
        var chkbox = row.cells[0].childNodes[0];
        if (null != chkbox && true == chkbox.checked) {
            tb.deleteRow(i);
            NoOfrows--;
            i--;
        }
    }
}
//*****************************************************************************
function deleteSelectedColoumns() {
    var tb=getTable();
    var NoOfcolumns = tb.rows[0].cells.length;
    
    for (var clm = 1; clm < NoOfcolumns; clm++) {
        var rw = tb.rows[0];
        var chkbox = rw.cells[clm].childNodes[0];
        
        console.log(clm, NoOfcolumns, chkbox);
        if (null != chkbox && true == chkbox.checked) {            
            //-----------------------------------------------------
            var lastrow = tb.rows;
            for (var x = 0; x < lastrow.length; x++) {
                tb.rows[x].deleteCell(clm);
            }
            //-----------------------------------------
            NoOfcolumns--;
            clm--;
        } 
    }
}

function deleteAllRows() {
    var tbl=getTable();
    lastRow = tbl.rows.length - 1; // set the last row index
    // delete rows with index greater then 0
    for (i = lastRow; i > 0; i--) {
        tbl.deleteRow(i);  //delete the row
    }
}
//*******************************************************************************
function getTable() {
    return document.getElementById('transcript2').getElementsByTagName("table")[1];
}
//*******************************************************************************
function addRow() {
    for(var i=0;i<4;i++)
        appendRow();
    tb=getTable(), row=tb.rows[tb.rows.length-3], index=1;
    for(j=0;j<2;j++) {
        row.cells[index].getElementsByTagName('div')[0].innerHTML='l';
        val=$('#taal').val().split('').reverse().join('');
        while (val > 0) {
            d=val%10;
            for(i=1;i<=d;i++)
               row.cells[index+i].getElementsByTagName('div')[0].innerHTML='-a';    
            index=index+d+1;
            row.cells[index].getElementsByTagName('div')[0].innerHTML='A';
            val=~~(val/10);            
        }
    }
    cell=row.cells[index].getElementsByTagName('div')[0].innerHTML='l';
}
function setNew() {
    $('#transcript2').html($('#skeleton').html());
    appendColumn();
    for(j=0;j<2;j++) {
        val=$('#taal').val();
        while (val > 0) {
            d=val%10;
            for(var i=0;i<=d;i++)
                appendColumn();
            val=~~(val/10);
        }
    }
    addRow();
}


