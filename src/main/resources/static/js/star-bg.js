(function () {
    const canvas = document.getElementById('starBg');
    if (!canvas) return;

    const ctx = canvas.getContext('2d');
    let width = window.innerWidth;
    let height = window.innerHeight;

    let mouseX = width / 2;
    let mouseY = height / 2;
    let currentOffsetX = 0;
    let currentOffsetY = 0;

    // 画布尺寸自适应
    function resize() {
        width = window.innerWidth;
        height = window.innerHeight;
        canvas.width = width;
        canvas.height = height;
    }
    resize();
    window.addEventListener('resize', resize);

    // 监听鼠标移动
    document.addEventListener('mousemove', (e) => {
        mouseX = e.clientX;
        mouseY = e.clientY;
    });

    // 生成星星
    const starCount = Math.floor((width * height) / 13000);
    const stars = [];

    for (let i = 0; i < starCount; i++) {
        stars.push({
            x: Math.random() * width,
            y: Math.random() * height,
            baseR: Math.random() * 1.2 + 0.3,
            alpha: Math.random() * 0.7 + 0.3,
            layer: Math.random() < 0.3 ? 0.25 : (Math.random() < 0.65 ? 0.55 : 0.85),
            twinkleSpeed: Math.random() * 0.018 + 0.004,
            offset: Math.random() * Math.PI * 2,
            driftX: (Math.random() - 0.5) * 0.02,
            driftY: (Math.random() - 0.5) * 0.015
        });
    }

    const connectDist = 120;

    function draw(time) {
        // 拖尾渐变背景
        ctx.fillStyle = 'rgba(7, 7, 12, 0.22)';
        ctx.fillRect(0, 0, width, height);

        // 鼠标视差平滑跟随
        const targetX = (mouseX - width / 2) * 0.045;
        const targetY = (mouseY - height / 2) * 0.045;
        currentOffsetX += (targetX - currentOffsetX) * 0.06;
        currentOffsetY += (targetY - currentOffsetY) * 0.06;

        // 绘制星星
        for (let i = 0; i < stars.length; i++) {
            const s = stars[i];

            // 视差位移
            const px = s.x + currentOffsetX * s.layer;
            const py = s.y + currentOffsetY * s.layer;

            // 边界循环
            let x = px;
            let y = py;
            if (x < 0) x += width;
            if (x > width) x -= width;
            if (y < 0) y += height;
            if (y > height) y -= height;

            // 闪烁
            const twinkle = Math.sin(time * s.twinkleSpeed + s.offset);
            const baseAlpha = s.alpha + twinkle * 0.25;

            // 鼠标靠近发光
            const dx = mouseX - x;
            const dy = mouseY - y;
            const dist = Math.sqrt(dx * dx + dy * dy);
            const glow = Math.max(0, 1 - dist / 280);

            const radius = s.baseR + glow * 1.8;
            const finalAlpha = Math.min(1, baseAlpha + glow * 0.55);

            // 橙白渐变星光
            const grad = ctx.createRadialGradient(x, y, 0, x, y, radius * 4.5);
            grad.addColorStop(0, `rgba(255, 255, 255, ${finalAlpha})`);
            grad.addColorStop(0.4, `rgba(255, 170, 70, ${finalAlpha * 0.6})`);
            grad.addColorStop(1, 'rgba(255, 138, 0, 0)');

            ctx.beginPath();
            ctx.arc(x, y, radius, 0, Math.PI * 2);
            ctx.fillStyle = grad;
            ctx.fill();

            // 缓慢漂移
            s.x += s.driftX;
            s.y += s.driftY;
            if (s.x < 0) s.x = width;
            if (s.x > width) s.x = 0;
            if (s.y < 0) s.y = height;
            if (s.y > height) s.y = 0;
        }

        // 星点连线（近距离）
        for (let i = 0; i < stars.length; i++) {
            for (let j = i + 1; j < stars.length; j++) {
                const a = stars[i];
                const b = stars[j];
                const ax = a.x + currentOffsetX * a.layer;
                const ay = a.y + currentOffsetY * a.layer;
                const bx = b.x + currentOffsetX * b.layer;
                const by = b.y + currentOffsetY * b.layer;

                const dx = ax - bx;
                const dy = ay - by;
                const dist = Math.sqrt(dx * dx + dy * dy);

                if (dist < connectDist) {
                    const alpha = (1 - dist / connectDist) * 0.2;
                    ctx.beginPath();
                    ctx.moveTo(ax, ay);
                    ctx.lineTo(bx, by);
                    ctx.strokeStyle = `rgba(255, 138, 0, ${alpha})`;
                    ctx.lineWidth = 0.6;
                    ctx.stroke();
                }
            }
        }

        requestAnimationFrame(draw);
    }

    requestAnimationFrame(draw);
})();
