document.addEventListener("DOMContentLoaded", function(){
    document.querySelectorAll('.locations li').forEach(function(li) {
        li.addEventListener('click', function() {
            let cityId = this.getAttribute('data-city-id');
            document.querySelector('.showtime-movie-detail').style.display = 'none';
            showInfo(cityId);
        });
    });
    function showInfo(cityId) {
        document.querySelectorAll('.locations li').forEach(function(elm) {
            elm.classList.remove('selected');
        });

        let selectedElement = document.querySelector(`.locations li[data-city-id="${cityId}"]`);
        if (selectedElement) {
            selectedElement.classList.add('selected');
        }

        fetch(`/cities/${cityId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                let info = document.getElementById('info');
                if (data && data.theaters && data.theaters.length > 0) {
                    let theatersHtml = '<ul>';
                    data.theaters.forEach(theater => {
                        theatersHtml += `<li onclick="handleClick('${theater.id}')">
                                ${theater.name}</li>`;
                    });
                    theatersHtml += '</ul>';
                    info.innerHTML = theatersHtml;
                } else if (data && (!data.theaters || data.theaters.length === 0)) {
                    info.innerHTML = '<p>No theaters available in this city.</p>';
                } else {
                    info.innerHTML = '<p>City not found.</p>';
                }
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
                let info = document.getElementById('info');
                info.innerHTML = '<p>Error fetching city information.</p>';
            });
    }
    window.handleClick = function (theaterId) {
        $.ajax({
            type: 'GET',
            url: `/theaters/detail/${theaterId}`,
            data: {},
            success: function(response) {
                document.querySelector('.showtime-movie-detail').style.display = 'block';
                $('.showtime-movie-detail').html(response);
                const event = new Event('contentLoaded');
                document.dispatchEvent(event);
            },
            error: function() {
                alert('Có lỗi xảy ra. Vui lòng thử lại sau.');
            }
        });
    }
    document.addEventListener('contentLoaded', function() {
        const datesContainer = document.querySelector('.dates');
        const prevButton = document.getElementById('prev');
        const nextButton = document.getElementById('next');
        const dateWidth = document.querySelector('.date').offsetWidth + 6;
        const visibledates = 10;
        const totaldates = document.querySelectorAll('.date').length;
        let currentIndex = 0;
        const dates = document.querySelectorAll('.date');

        dates.forEach(date => {
            date.addEventListener('click', function() {
                dates.forEach(item => item.classList.remove('selected'));
                this.classList.add('selected');
            });
        });

        function updatedatePosition() {
            const offset = -currentIndex * dateWidth;
            datesContainer.style.transform = `translateX(${offset}px)`;
        }

        prevButton.addEventListener('click', () => {
            if (currentIndex > 0) {
                currentIndex--;
                updatedatePosition();
            }
        });

        nextButton.addEventListener('click', () => {
            if (currentIndex < totaldates - visibledates) {
                currentIndex++;
                updatedatePosition();
            }
        });
        // Initial display
        updatedatePosition();
        document.querySelectorAll('.dates .date').forEach(function(li) {
            li.addEventListener('click', function() {
                var day = this.querySelector('strong').textContent;
                var month = this.querySelector('span').textContent;
                var year = (new Date()).getFullYear();
                var dateStr = year + '-' + month + '-' + day;

                let theaterId = this.getAttribute('data-theater-id');

                showDetailShowtime(theaterId, dateStr);
            });
        });

    });
    function showDetailShowtime(theaterId, dateStr){
        $.ajax({
            type: 'GET',
            url: `/theaters/detail/showtime/${theaterId}`,
            data: {
                date: dateStr,
            },
            success: function(response) {
                $('.movie-list').html(response);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('Có lỗi xảy ra. Vui lòng thử lại sau.');
            }
        });
    }
}, false);