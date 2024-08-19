document.addEventListener("DOMContentLoaded", function() {
    const seats = document.querySelectorAll('label[data-seat-type]');
    const current_language_code = document.getElementById('current_language_code').textContent;
    var seats_price = 0;
    var combo_price = 0;
    let seat_array = [];
    seats.forEach(seat => {
        console.log(JSON.parse(seat.getAttribute('data-seat')));
        setSeatColor(seat);
    });
    window.addEventListener('pageshow', function(event) {
        seats.forEach(seat => {
            const checkbox = document.getElementById(seat.getAttribute("for"));
            checkbox.checked = false;
        });
    });
    seats.forEach(seat => {
        const available = seat.getAttribute('data-available') === 'true';
        const selected = seat.getAttribute('data-selected') === 'true';
        const price = parseInt(seat.getAttribute('data-price'));
        const checkbox = document.getElementById(seat.getAttribute("for"))
        if (!available || selected) {
            seat.style.pointerEvents = 'none';
            return;
        }
        seat.addEventListener('click', function() {
            const isSelected = seat.getAttribute('data-checked') === 'true';

            if (isSelected) {
                seats_price -= price;
                seat_array = seat_array.filter(item => item.id !== seat.id);
                checkbox.checked = true;
                seat.setAttribute('data-checked', 'false');
                setSeatColor(seat);
            } else {
                seats_price += price;
                seat_array.push({id: seat.id, name: seat.textContent});
                checkbox.checked = false;
                seat.setAttribute('data-checked', 'true');
                seat.style.backgroundColor = '#b11500'; // Màu của "Ghế đang chọn"
            }
            updatePrice();
        });
    });
    document.getElementById('form-chonghe').addEventListener('submit', function(event) {
        if (seat_array.length === 0) {
            event.preventDefault();
            if(current_language_code === 'vi'){
                alert('Bạn cần chọn ít nhất một ghế trước khi tiếp tục.');
            }else{
                alert('You need to select at least one seat before continuing.');
            }
        }
    });
    function updatePrice(){
        const seat_ticket_price = document.getElementById('seat_ticket_price');
        const combo_ticket_price = document.getElementById('combo_ticket_price');
        const final_ticket_Element = document.getElementById('final_ticket_price');
        const total_seats_array = document.getElementById('total_seats_array');
        seat_ticket_price.textContent = seats_price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
        combo_ticket_price.textContent = combo_price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
        const final_ticket_price = seats_price + combo_price;
        final_ticket_Element.textContent = final_ticket_price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });;
        total_seats_array.innerHTML = seat_array.map(seat=>seat.name).join(', ');
    }


    function setSeatColor(seat) {
        const seatType = seat.getAttribute('data-seat-type');
        const available = seat.getAttribute('data-available') === 'true';
        const selected = seat.getAttribute('data-selected') === 'true';
        console.log(seat , seatType, available, selected);
        let backgroundColor = '';

        if (!available) {
            backgroundColor = '#343433';
        }
        else if(selected){
            backgroundColor = '#bbbbbb';
        }
        else {
            switch(seatType) {
                case 'STANDARD':
                    backgroundColor = '#278ac7';
                    break;
                case 'VIP':
                    backgroundColor = '#01c73c';
                    break;
                case 'COUPLE':
                    backgroundColor = '#ff69b4';
                    break;
                default:
                    backgroundColor = '#ffffff';
            }
        }
        seat.style.backgroundColor = backgroundColor;
    }
});
