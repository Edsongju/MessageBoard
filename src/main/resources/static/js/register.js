document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('registerForm');

    form.addEventListener('submit', function(e) {
        const username = document.getElementById('username').value.trim();
        const password = document.getElementById('password').value.trim();
        const rePassword = document.getElementById('rePassword').value.trim();

        if (!username) {
            alert('请输入用户名');
            e.preventDefault();
            return false;
        }
        if (username.length < 4 || username.length > 16) {
            alert('用户名长度为4-16位');
            e.preventDefault();
            return false;
        }
        if (!password) {
            alert('请输入密码');
            e.preventDefault();
            return false;
        }
        if (password.length < 6) {
            alert('密码至少6位');
            e.preventDefault();
            return false;
        }
        if (password !== rePassword) {
            alert('两次输入的密码不一致');
            e.preventDefault();
            return false;
        }
    });
});
