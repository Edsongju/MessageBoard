// 展开/收起回复
function toggleReply(messageId) {
    const section = document.getElementById('reply-' + messageId);
    if (section) {
        section.classList.toggle('show');
    }
}

document.addEventListener('DOMContentLoaded', function() {
    // 删除二次确认
    const deleteBtns = document.querySelectorAll('.delete-btn');
    deleteBtns.forEach(btn => {
        btn.addEventListener('click', function(e) {
            if (!confirm('确定执行删除操作？删除后不可恢复。')) {
                e.preventDefault();
            }
        });
    });

    // 可见性单选联动
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
});
