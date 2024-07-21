<<<<<<< HEAD
// dropdown.js
document.addEventListener('DOMContentLoaded', function() {
    const dropdowns = document.querySelectorAll('.dropdown');
  
    dropdowns.forEach(dropdown => {
      const select = dropdown.querySelector('.select');
      const caret = dropdown.querySelector('.caret');
      const option = dropdown.querySelector('.option');
      const options = dropdown.querySelectorAll('.option li');
      const selected = dropdown.querySelector('.selected');
  
      select.addEventListener('click', () => {
        select.classList.toggle('select-clicked');
        caret.classList.toggle('caret-rotate');
        option.classList.toggle('option-open');
      });
  
      options.forEach(chooseoption => {
        chooseoption.addEventListener('click', () => {
          selected.innerText = chooseoption.innerText;
          select.classList.remove('select-clicked');
          caret.classList.remove('caret-rotate');
          option.classList.remove('option-open');
  
          options.forEach(option => {
            option.classList.remove('active');
          });
  
          chooseoption.classList.add('active');
        });
      });
    });
  });
=======
// dropdown.js
document.addEventListener('DOMContentLoaded', function() {
    const dropdowns = document.querySelectorAll('.dropdown');
  
    dropdowns.forEach(dropdown => {
      const select = dropdown.querySelector('.select');
      const caret = dropdown.querySelector('.caret');
      const option = dropdown.querySelector('.option');
      const options = dropdown.querySelectorAll('.option li');
      const selected = dropdown.querySelector('.selected');
  
      select.addEventListener('click', () => {
        select.classList.toggle('select-clicked');
        caret.classList.toggle('caret-rotate');
        option.classList.toggle('option-open');
      });
  
      options.forEach(chooseoption => {
        chooseoption.addEventListener('click', () => {
          selected.innerText = chooseoption.innerText;
          select.classList.remove('select-clicked');
          caret.classList.remove('caret-rotate');
          option.classList.remove('option-open');
  
          options.forEach(option => {
            option.classList.remove('active');
          });
  
          chooseoption.classList.add('active');
        });
      });
    });
  });
>>>>>>> 0e7539ead2915ea78c766e997495ae227db5cafa
