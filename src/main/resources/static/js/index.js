document.addEventListener("DOMContentLoaded", function () {
    const slides = document.querySelectorAll(".swiper-top-slide");
    const dots = document.querySelectorAll(".swiper-top-dot");

    if (slides.length === 0) return;

    let currentIndex = 0;

    function showSlide(index) {
        slides.forEach((slide, i) => {
            slide.classList.toggle("active", i === index);
        });
        dots.forEach((dot, i) => {
            dot.classList.toggle("active", i === index);
        });
        currentIndex = index;
    }

    dots.forEach((dot, i) => {
        dot.addEventListener("click", () => showSlide(i));
    });

    setInterval(() => {
        const nextIndex = (currentIndex + 1) % slides.length;
        showSlide(nextIndex);
    }, 4000);
});
