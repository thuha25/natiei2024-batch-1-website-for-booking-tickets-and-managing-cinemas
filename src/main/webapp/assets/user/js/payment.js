document.addEventListener("DOMContentLoaded", function() {
   const btn_point_use = document.getElementById('btn_point_use');
   const discountPointElement = document.getElementById('discount_point');
   const discountTotalElement = document.getElementById('discount_total');
   const discountTotalFoot = document.getElementById('discount_ticket_price');
   const point_input = document.getElementById('point_input');
   const totalPrice = parseInt(document.getElementById('total_price').getAttribute('data-value'));
   const finalPriceElement = document.getElementById('final_price');
   const finalTicketPriceElement = document.getElementById('final_ticket_price');
   const max_point = point_input.getAttribute("max");
   const current_language_code = document.getElementById('current_language_code').textContent;
   var pre_point = 0;
   btn_point_use.addEventListener('click', function () {
       let point_input_value = parseInt(point_input.value);
       if(point_input_value > max_point || point_input_value < 0){
           if(current_language_code === 'vi'){
               alert("Bạn hãy nhập trong khoảng từ 0 đến point của bạn!")
           }else{
               alert("Please enter between 0 and your point!")
           }
           return;
       }
       let point = point_input_value - pre_point;
       pre_point = point_input_value;
       discountPointElement.textContent = (point_input_value*1000).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
       let discount_total_value = parseInt(discountTotalElement.getAttribute('data-value'));
       discount_total_value += point*1000;
       discountTotalElement.setAttribute('data-value', discount_total_value+"");
       discountTotalElement.textContent = discount_total_value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
       discountTotalFoot.textContent = discountTotalElement.textContent;
       finalPriceElement.textContent = (totalPrice - discount_total_value).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
       finalTicketPriceElement.textContent = finalPriceElement.textContent;
   })
});