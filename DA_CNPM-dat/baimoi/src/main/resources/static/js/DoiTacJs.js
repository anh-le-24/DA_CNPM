
function toggleSidebar() {
  var sidebarMenu = document.getElementById('sidebarMenu');
  var sidebarToggle = document.getElementById('sidebarToggle');
  var sidebar = document.querySelector('.sidebar');

  sidebarMenu.classList.toggle('active');
  sidebarToggle.textContent = sidebarMenu.classList.contains('active') ? '✕' : '☰';

  sidebar.style.width = sidebarMenu.classList.contains('active') ? '400px' : '50px'; // Điều chỉnh chiều rộng của thanh sidebar
}

function clickDrop1() {
  document.getElementById("drop-click1").classList.toggle('show');
}
function clickDrop2() {
  document.getElementById("drop-click2").classList.toggle('show');
}
function clickDrop3() {
  document.getElementById("drop-click3").classList.toggle('show');
}

// Mở thông báo xác nhận của trang Chờ xác nhận
function openForm(maddb) {
document.getElementById("xacNhanMaddb").value = maddb;
document.getElementById("myForm").style.display = "block";
}

function closeForm() {
document.getElementById("myForm").style.display = "none";
}

function openFormHuy(maddb) {
document.getElementById("huyMaddb").value = maddb;
document.getElementById("myFormHuy").style.display = "block";
}

function closeFormHuy() {
document.getElementById("myFormHuy").style.display = "none";
}

function setLyDoHuy() {
var lyDo = document.getElementById("lyDoHuyInput").value;
if (lyDo) {
    document.getElementById("lyDoHuyHidden").value = lyDo;
    return true;
} else {
    alert("Vui lòng nhập lý do hủy");
    return false;
}
}

// Mở thông báo xác nhận của trang Đã xác nhận
function openFormDaXN(maddb) {
  document.getElementById("xacNhanMaddbDaXN").value = maddb;
  document.getElementById("myFormDaXN").style.display = "block";
}

function setSoTienDaXN() {
var soTien = document.getElementById("soTienInputDaXN").value;
if (soTien) {
    document.getElementById("soTienHiddenDaXN").value = lyDo;
    return true;
} else {
    alert("Vui lòng nhập số tiền");
    return false;
}
}

function closeFormDaXN() {
  document.getElementById("myFormDaXN").style.display = "none";
}

function openFormHuyDaXN(maddb) {
  document.getElementById("huyMaddbDaXN").value = maddb;
  document.getElementById("myFormHuyDaXN").style.display = "block";
}
function closeFormHuyDaXN() {
  document.getElementById("myFormHuyDaXN").style.display = "none";
}


function setSoTienDaXN() {
var lyDo = document.getElementById("soTienInputDaXN").value;
if (lyDo) {
    document.getElementById("soTienHiddenDaXN").value = lyDo;
    return true;
} else {
    alert("Vui lòng nhập lý do hủy");
    return false;
}
}

function setLyDoHuyDaXN() {
  var lyDo = document.getElementById("lyDoHuyInputDaXN").value;
  if (lyDo) {
      document.getElementById("lyDoHuyHiddenDaXN").value = lyDo;
      return true;
  } else {
      alert("Vui lòng nhập lý do hủy");
      return false;
  }
  }
