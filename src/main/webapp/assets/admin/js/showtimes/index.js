document.addEventListener("DOMContentLoaded", function () {
    const provinceSelect = document.getElementById("province");
    const theaterSelect = document.getElementById("theater");

    // Function to fetch and populate theaters based on the selected province
    async function loadTheaters(cityId) {
        if (cityId) {
            try {
                // Fetch theaters for the selected city
                const response = await fetch(`/cities/${cityId}`);

                // Check if the response is OK
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                // Parse the JSON data
                const data = await response.json();

                // Populate the theater select dropdown
                if (data.theaters && data.theaters.length > 0) {
                    theaterSelect.innerHTML = "<option value=''>Chọn Rạp</option>";
                    data.theaters.forEach(theater => {
                        theaterSelect.innerHTML += `<option value="${theater.id}">${theater.name}</option>`;
                    });
                    theaterSelect.disabled = false;
                } else {
                    theaterSelect.innerHTML = '<option value="">Không có rạp</option>';
                    theaterSelect.disabled = true;
                }
            } catch (error) {
                console.error('Error loading theaters:', error);
                theaterSelect.innerHTML = '<option value="">Không thể tải dữ liệu</option>';
                theaterSelect.disabled = true;
            }
        } else {
            theaterSelect.innerHTML = '<option value="">Chọn Rạp</option>';
            theaterSelect.disabled = true;
        }
    }

    // Event listener for province selection change
    provinceSelect.addEventListener("change", function () {
        const cityId = provinceSelect.value;
        loadTheaters(cityId);
    });
});
