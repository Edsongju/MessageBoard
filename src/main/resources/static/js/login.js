// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('loginForm');

    form.addEventListener('submit', function(e) {
        const username = document.getElementById('username').value.trim();
        const password = document.getElementById('password').value.trim();

        // 前端非空校验
        if (!username) {
            alert('请输入用户名');
            e.preventDefault();
            return false;
        }
        if (!password) {
            alert('请输入密码');
            e.preventDefault();
            return false;
        }
    });
});
