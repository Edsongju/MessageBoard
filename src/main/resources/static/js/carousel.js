// 首页轮播图
(function() {
    const carousel = document.querySelector('.carousel');
    if (!carousel) return;

    const items = carousel.querySelectorAll('.carousel-item');
    const indicators = carousel.querySelectorAll('.indicator');
    const prevBtn = carousel.querySelector('.prev');
    const nextBtn = carousel.querySelector('.next');

    let currentIndex = 0;
    let autoTimer = null;
    const AUTO_PLAY_SPEED = 4500; // 自动播放间隔

    // 切换到指定轮播项
    function switchTo(index) {
        // 边界循环
        if (index < 0) index = items.length - 1;
        if (index >= items.length) index = 0;

        // 切换图片
        items.forEach(item => item.classList.remove('active'));
        items[index].classList.add('active');

        // 切换指示器
        indicators.forEach(ind => ind.classList.remove('active'));
        indicators[index].classList.add('active');

        currentIndex = index;
    }

    // 上一张
    function prevSlide() {
        switchTo(currentIndex - 1);
        resetAutoPlay();
    }

    // 下一张
    function nextSlide() {
        switchTo(currentIndex + 1);
        resetAutoPlay();
    }

    // 启动自动播放
    function startAutoPlay() {
        autoTimer = setInterval(nextSlide, AUTO_PLAY_SPEED);
    }

    // 重置自动播放（手动切换后重新计时）
    function resetAutoPlay() {
        clearInterval(autoTimer);
        startAutoPlay();
    }

    // 绑定事件
    prevBtn.addEventListener('click', prevSlide);
    nextBtn.addEventListener('click', nextSlide);

    // 指示器点击跳转
    indicators.forEach((dot, idx) => {
        dot.addEventListener('click', () => {
            switchTo(idx);
            resetAutoPlay();
        });
    });

    // 鼠标悬停暂停，离开继续
    carousel.addEventListener('mouseenter', () => clearInterval(autoTimer));
    carousel.addEventListener('mouseleave', startAutoPlay);

    // 初始化启动
    startAutoPlay();
})();
