document.addEventListener("DOMContentLoaded", function() {
    window.updateCount = function (element, delta) {
        const index = element.getAttribute('data-index');
        const input = document.querySelector(`input[name='foodSelections[${index}].count']`);
        const display = document.getElementById(`quantity-display-${index}`);
        let currentValue = parseInt(input.value) || 0;
        currentValue += delta;
        if (currentValue < 0) {
            currentValue = 0;
        }
        input.value = currentValue;
        display.textContent = currentValue;

        updateTotalPrice();
    }
    function updateTotalPrice() {
        const seatPriceElement = document.getElementById('seat_ticket_price');
        const seatPrice = parseFloat(seatPriceElement.textContent.replace(/[^\d,]/g, '').replace(',', '.'));
        let totalComboPrice = 0;
        document.querySelectorAll('.products-list .food').forEach((item, index) => {
            const quantityElement = document.querySelector(`input[name='foodSelections[${index}].count']`);
            const quantity = parseInt(quantityElement.value) || 0;
            totalComboPrice += calculateComboPrice(index, quantity);
        });

        const totalPrice = seatPrice + totalComboPrice;
        const comboElement = document.getElementById('combo_ticket_price');
        comboElement.textContent = totalComboPrice.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });

        const finalElement = document.getElementById('final_ticket_price');
        finalElement.textContent = totalPrice.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
    }
    function calculateComboPrice(index, quantity) {
        const priceElement = document.querySelector(`.products-list .item:nth-child(${index + 1}) .price-box .price`); // Chọn phần tử giá dựa trên index
        const foodPrice = parseFloat(priceElement.textContent.replace(/[^\d,]/g, '').replace(',', '.'));
        return quantity * foodPrice;
    }
});