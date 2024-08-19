document.addEventListener("DOMContentLoaded", function() {
    let countdownMinutes = 4;
    let countdownSeconds = 25;
    let totalTime = countdownMinutes * 60 + countdownSeconds;

    function startCountdown() {
        let countdownElement = document.getElementById('countdown');
        let minutesSpan = countdownElement.querySelector('.cgvminutes');
        let secondsSpan = countdownElement.querySelector('.cgvseconds');

        let countdownInterval = setInterval(() => {
            let minutes = Math.floor(totalTime / 60);
            let seconds = totalTime % 60;

            minutesSpan.textContent = minutes;
            secondsSpan.textContent = seconds < 10 ? '0' + seconds : seconds;

            if (totalTime <= 0) {
                clearInterval(countdownInterval);
                alert('Thời gian phiên đặt vé đã hết.');
                window.location.href = "/time-expired";
            }

            totalTime--;
        }, 1000);
    }

    window.onload = startCountdown;
});