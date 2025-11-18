// Smooth scrolling for navigation links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    });
});

// Add active class to navigation links based on scroll position
const sections = document.querySelectorAll('section');
const navLinks = document.querySelectorAll('.nav-text');

window.addEventListener('scroll', () => {
    let current = '';
    sections.forEach(section => {
        const sectionTop = section.offsetTop;
        const sectionHeight = section.clientHeight;
        if (scrollY >= (sectionTop - 200)) {
            current = section.getAttribute('id');
        }
    });

    navLinks.forEach(link => {
        link.classList.remove('active');
        if (link.getAttribute('href') === `#${current}`) {
            link.classList.add('active');
        }
    });
});

// Add hover effects to project items
document.querySelectorAll('.project-image').forEach(project => {
    project.addEventListener('mouseenter', () => {
        project.style.transform = 'translateY(-5px)';
    });

    project.addEventListener('mouseleave', () => {
        project.style.transform = 'translateY(0)';
    });
});

// Add fade-in animation for elements
const observerOptions = {
    threshold: 0.1,
    rootMargin: '0px 0px -50px 0px'
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.style.opacity = '1';
            entry.target.style.transform = 'translateY(0)';
        }
    });
}, observerOptions);

// Observe all sections for fade-in effect
document.querySelectorAll('section').forEach(section => {
    section.style.opacity = '0';
    section.style.transform = 'translateY(20px)';
    section.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
    observer.observe(section);
});

// Contact Section Logic
document.addEventListener('DOMContentLoaded', function() {
    const showContactsBtn = document.getElementById('showContacts');
    const contactDetails = document.getElementById('contactDetails');

    if (showContactsBtn && contactDetails) {
        // Скрываем контакты при загрузке
        contactDetails.style.display = 'none';

        showContactsBtn.addEventListener('click', function() {
            if (contactDetails.style.display === 'none') {
                contactDetails.style.display = 'block';
                this.textContent = 'Скрыть контакты';
                this.style.backgroundColor = 'var(--lightest-navy)';
            } else {
                contactDetails.style.display = 'none';
                this.textContent = 'Сказать Привет';
                this.style.backgroundColor = 'transparent';
            }
        });
    }

    // Обработчики для кликабельных контактов
    const contactCards = document.querySelectorAll('.contact-card');
    contactCards.forEach(card => {
        card.addEventListener('click', function(e) {
            // Для GitHub открываем в новой вкладке
            if (this.href && this.target === '_blank') {
                return; // Разрешаем стандартное поведение
            }

            // Для телефона и email тоже разрешаем стандартное поведение
            if (this.href && (this.href.startsWith('tel:') || this.href.startsWith('mailto:'))) {
                return;
            }

            e.preventDefault();
        });
    });
});

// Функция показа уведомления (оставь на будущее)
function showNotification(message) {
    const notification = document.createElement('div');
    notification.className = 'notification';
    notification.textContent = message;
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        background: var(--green);
        color: var(--navy);
        padding: 1rem 1.5rem;
        border-radius: 5px;
        font-family: 'SF Mono', monospace;
        font-weight: 600;
        z-index: 1000;
        box-shadow: 0 5px 15px rgba(0,0,0,0.3);
    `;

    document.body.appendChild(notification);

    setTimeout(() => {
        notification.remove();
    }, 3000);
}

// Валидация формы обратной связи
document.getElementById('feedbackForm')?.addEventListener('submit', function(e) {
    e.preventDefault();

    const email = this.querySelector('input[type="email"]').value;
    const name = this.querySelector('input[type="text"]').value;
    const message = this.querySelector('textarea').value;

    // Валидация email
    if (!email.includes('@') || !email.includes('.')) {
        alert('Пожалуйста, введите корректный email адрес');
        return;
    }

    // Валидация имени
    if (name.trim().length < 2) {
        alert('Имя должно содержать минимум 2 символа');
        return;
    }

    // Валидация сообщения
    if (message.trim().length < 10) {
        alert('Сообщение должно содержать минимум 10 символов');
        return;
    }

    // Если все проверки пройдены
    alert('Сообщение отправлено! Я свяжусь с вами в ближайшее время.');
    this.reset();
});
});