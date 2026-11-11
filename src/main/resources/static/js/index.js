// 轮播图自动播放
document.addEventListener('DOMContentLoaded', function () {
    const slides = document.querySelectorAll('.swiper-slide');
    const dots = document.querySelectorAll('.swiper-dots .dot');
    let currentIndex = 0;
    let timer = null;

    // 切换到指定索引
    function switchSlide(index) {
        slides.forEach((slide, i) => {
            slide.classList.toggle('active', i === index);
        });
        dots.forEach((dot, i) => {
            dot.classList.toggle('active', i === index);
        });
        currentIndex = index;
    }

    // 自动轮播
    function startAutoPlay() {
        timer = setInterval(() => {
            let next = (currentIndex + 1) % slides.length;
            switchSlide(next);
        }, 4000);
    }

    // 点击圆点切换
    dots.forEach((dot, index) => {
        dot.addEventListener('click', () => {
            switchSlide(index);
            clearInterval(timer);
            startAutoPlay();
        });
    });

    startAutoPlay();
});
