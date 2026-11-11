// 展开/收起回复区
function toggleReply(messageId) {
    const section = document.getElementById('reply-' + messageId);
    if (section) {
        section.classList.toggle('show');
    }
}

// 删除确认
document.addEventListener('DOMContentLoaded', function() {
    // 所有删除按钮添加二次确认
    const deleteBtns = document.querySelectorAll('.delete-btn');
    deleteBtns.forEach(btn => {
        btn.addEventListener('click', function(e) {
            if (!confirm('确定执行该删除操作吗？删除后不可恢复。')) {
                e.preventDefault();
            }
        });
    });

    // 可见性单选框联动：选指定用户时显示输入框
    const visibilityRadios = document.querySelectorAll('input[name="visibility"]');
    const usernameInput = document.getElementById('visibleUsernames');

    if (visibilityRadios.length && usernameInput) {
        visibilityRadios.forEach(radio => {
            radio.addEventListener('change', function() {
                if (this.value === '2') {
                    usernameInput.style.display = 'block';
                } else {
                    usernameInput.style.display = 'none';
                    usernameInput.value = '';
                }
            });
        });
    }

    // 搜索表单提交
    const searchForm = document.getElementById('searchForm');
    if (searchForm) {
        searchForm.addEventListener('submit', function(e) {
            const keyword = this.querySelector('input[name="keyword"]').value.trim();
            if (!keyword) {
                // 空关键词直接跳转，不带参数
                e.preventDefault();
                window.location.href = this.action;
            }
        });
    }
});
