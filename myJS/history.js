var ith = myArray.length-1;

function KeyPress(e) {
  var evtobj = window.event? event : e;
        if (evtobj.keyCode == 38 && ith >= 11) {
          document.getElementById('end').value = myArray[ith-11];
                document.getElementById('start').value = myArray[ith-10];
                document.getElementById('eemax').value = myArray[ith-9];
                document.getElementById('eemin').value = myArray[ith-8];
                document.getElementById('ermax').value = myArray[ith-7];
                document.getElementById('ermin').value = myArray[ith-6];
                document.getElementById('keyword1').value = myArray[ith-5];
                document.getElementById('lat2').value = myArray[ith-4];
                document.getElementById('long2').value = myArray[ith-3];
                document.getElementById('lat1').value = myArray[ith-2];
                document.getElementById('long1').value = myArray[ith-1];
                document.getElementById('db').value = myArray[ith];
                $('#dbselect').val(myArray[ith]);
    if(ith-12 >= 0){
                  ith -= 12;
    }
        }
  if (evtobj.keyCode == 40 && ith+12 > myArray.length) {
                document.getElementById('end').value = '';
                document.getElementById('start').value = '';
                document.getElementById('eemax').value = '';
                document.getElementById('eemin').value = '';
                document.getElementById('ermax').value = '';
                document.getElementById('ermin').value = '';
                document.getElementById('keyword1').value = '';
                document.getElementById('lat2').value = '';
                document.getElementById('long2').value = '';
                document.getElementById('lat1').value = '';
                document.getElementById('long1').value = '';
                document.getElementById('db').value = '';
                $('#dbselect').val('Choose existing database...');
        }
        if (evtobj.keyCode == 40 && ith+12 < myArray.length) {
          ith += 12;
                document.getElementById('end').value = myArray[ith-11];
                document.getElementById('start').value = myArray[ith-10];
                document.getElementById('eemax').value = myArray[ith-9];
                document.getElementById('eemin').value = myArray[ith-8];
                document.getElementById('ermax').value = myArray[ith-7];
                document.getElementById('ermin').value = myArray[ith-6];
                document.getElementById('keyword1').value = myArray[ith-5];
                document.getElementById('lat2').value = myArray[ith-4];
                document.getElementById('long2').value = myArray[ith-3];
                document.getElementById('lat1').value = myArray[ith-2];
                document.getElementById('long1').value = myArray[ith-1];
                document.getElementById('db').value = myArray[ith];
                $('#dbselect').val(myArray[ith]);
        }
}
document.onkeydown = KeyPress;

