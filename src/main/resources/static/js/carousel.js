(function() {
    const carousel = document.querySelector('.carousel');
    if (!carousel) return;

    const items = carousel.querySelectorAll('.carousel-item');
    const indicators = carousel.querySelectorAll('.indicator');
    const prevBtn = carousel.querySelector('.prev');
    const nextBtn = carousel.querySelector('.next');

    let currentIndex = 0;
    let autoTimer = null;
    const AUTO_PLAY_SPEED = 4500;

    function switchTo(index) {
        if (index < 0) index = items.length - 1;
        if (index >= items.length) index = 0;

        items.forEach(item => item.classList.remove('active'));
        items[index].classList.add('active');

        indicators.forEach(ind => ind.classList.remove('active'));
        indicators[index].classList.add('active');

        currentIndex = index;
    }

    function prevSlide() {
        switchTo(currentIndex - 1);
        resetAutoPlay();
    }

    function nextSlide() {
        switchTo(currentIndex + 1);
        resetAutoPlay();
    }

    function startAutoPlay() {
        autoTimer = setInterval(nextSlide, AUTO_PLAY_SPEED);
    }

    function resetAutoPlay() {
        clearInterval(autoTimer);
        startAutoPlay();
    }

    prevBtn.addEventListener('click', prevSlide);
    nextBtn.addEventListener('click', nextSlide);

    indicators.forEach((dot, idx) => {
        dot.addEventListener('click', () => {
            switchTo(idx);
            resetAutoPlay();
        });
    });

    carousel.addEventListener('mouseenter', () => clearInterval(autoTimer));
    carousel.addEventListener('mouseleave', startAutoPlay);

    startAutoPlay();
})();
