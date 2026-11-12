(function () {
    const canvas = document.createElement('canvas');
    canvas.id = 'starBg';
    document.body.prepend(canvas);

    const ctx = canvas.getContext('2d');
    let width = window.innerWidth;
    let height = window.innerHeight;

    let mouseX = width / 2;
    let mouseY = height / 2;
    let currentOffsetX = 0;
    let currentOffsetY = 0;

    // 常态流动速度：调慢约 50%，更平缓
    const FLOW_X = 0.04;
    const FLOW_Y = 0.04;

    function resize() {
        width = window.innerWidth;
        height = window.innerHeight;
        canvas.width = width;
        canvas.height = height;
    }
    resize();
    window.addEventListener('resize', resize);

    document.addEventListener('mousemove', (e) => {
        mouseX = e.clientX;
        mouseY = e.clientY;
    });

    const starCount = Math.floor((width * height) / 13000);
    const stars = [];

    for (let i = 0; i < starCount; i++) {
        stars.push({
            x: Math.random() * width,
            y: Math.random() * height,
            baseR: Math.random() * 1.2 + 0.3,
            alpha: Math.random() * 0.45 + 0.45,
            layer: Math.random() < 0.3 ? 0.25 : (Math.random() < 0.65 ? 0.55 : 0.85),
            twinkleSpeed: Math.random() * 0.018 + 0.004,
            offset: Math.random() * Math.PI * 2,
            driftX: (Math.random() - 0.5) * 0.03,
            driftY: (Math.random() - 0.5) * 0.02
        });
    }

    const connectDist = 120;

    function draw(time) {
        ctx.fillStyle = 'rgba(7, 9, 14, 0.2)';
        ctx.fillRect(0, 0, width, height);

        const targetX = (mouseX - width / 2) * 0.045;
        const targetY = (mouseY - height / 2) * 0.045;
        currentOffsetX += (targetX - currentOffsetX) * 0.06;
        currentOffsetY += (targetY - currentOffsetY) * 0.06;

        for (let i = 0; i < stars.length; i++) {
            const s = stars[i];

            const px = s.x + currentOffsetX * s.layer + FLOW_X * s.layer;
            const py = s.y + currentOffsetY * s.layer + FLOW_Y * s.layer;

            let x = px;
            let y = py;
            if (x < -10) x += width + 20;
            if (x > width + 10) x -= width + 20;
            if (y < -10) y += height + 20;
            if (y > height + 10) y -= height + 20;

            s.x = x;
            s.y = y;

            const twinkle = Math.sin(time * s.twinkleSpeed + s.offset);
            const baseAlpha = s.alpha + twinkle * 0.25;

            const dx = mouseX - x;
            const dy = mouseY - y;
            const dist = Math.sqrt(dx * dx + dy * dy);
            const glow = Math.max(0, 1 - dist / 280);

            const radius = s.baseR + glow * 1.5;
            const finalAlpha = Math.min(1, baseAlpha + glow * 0.6);

            const grad = ctx.createRadialGradient(x, y, 0, x, y, radius * 4);
            grad.addColorStop(0, `rgba(245, 250, 255, ${finalAlpha})`);
            grad.addColorStop(0.4, `rgba(200, 225, 255, ${finalAlpha * 0.5})`);
            grad.addColorStop(1, 'rgba(180, 210, 255, 0)');

            ctx.beginPath();
            ctx.arc(x, y, radius, 0, Math.PI * 2);
            ctx.fillStyle = grad;
            ctx.fill();

            s.x += s.driftX;
            s.y += s.driftY;
        }

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
                    ctx.strokeStyle = `rgba(200, 225, 255, ${alpha})`;
                    ctx.lineWidth = 0.6;
                    ctx.stroke();
                }
            }
        }

        requestAnimationFrame(draw);
    }

    requestAnimationFrame(draw);
})();
