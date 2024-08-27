document.addEventListener("DOMContentLoaded", function () {
    const theaterSelect = document.getElementById("theater");
    const screenSelect = document.getElementById("screen");
    async function loadScreens(theaterId) {
        if (theaterId) {
            try {
                // Fetch screens for the selected theater
                const response = await fetch(`/theaters/${theaterId}`);

                // Check if the response is OK
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                // Parse the JSON data
                const data = await response.json();

                // Populate the screen select dropdown
                if (data.screens && data.screens.length > 0) {
                    screenSelect.innerHTML = "<option value=''>Chọn phòng chiếu</option>";
                    data.screens.forEach(screen => {
                        screenSelect.innerHTML += `<option value="${screen.id}">${screen.name}</option>`;
                    });
                } else {
                    screenSelect.innerHTML = '<option value="">Không có phòng chiếu</option>';
                }
            } catch (error) {
                screenSelect.innerHTML = '<option value="">Không thể tải dữ liệu</option>';
            }
        } else {
            screenSelect.innerHTML = '<option value="">Chọn phòng chiếu</option>';
        }
    }

    // Event listener for theater selection change
    theaterSelect.addEventListener("change", function () {
        const theaterId = theaterSelect.value;
        loadScreens(theaterId);
    });
});