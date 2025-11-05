let page = 0;
const size = 10;

const tbody = document.getElementById("books-body");
const loadMoreBtn = document.getElementById("loadMoreBtn");
const searchInput = document.getElementById("searchInput");

let currentQuery = "";

// Форматирование даты (если потребуется)
function formatDate(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}.${month}.${year}`;
}

// Загрузка данных с сервера
async function loadBooks(pageNum, query = "") {
    let url;
    if (query && query.length >= 3) {
        url = `/api/books/search?q=${encodeURIComponent(query)}&page=${pageNum}&size=${size}`;
    } else {
        url = `/api/books/page?page=${pageNum}&size=${size}`;
    }

    const res = await fetch(url);
    if (!res.ok) return { content: [], hasMore: false };
    return await res.json();
}

// Отрисовка таблицы
function renderBooks(books, append = false) {
    if (!append) tbody.innerHTML = "";
    books.forEach(b => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td><a href="/books/edit/${b.id}" class="icon-link" title="Редактировать">🔃</a></td>
            <td>${b.title || '-'}</td>
            <td>${b.author || '-'}</td>
            <td>${b.isbn || '-'}</td>
            <td><a href="#" class="icon-link delete-link" data-id="${b.id}" title="Удалить">❌</a></td>
        `;
        tbody.appendChild(row);
    });
}

// Кнопка "Показать ещё"
loadMoreBtn?.addEventListener("click", async () => {
    page++;
    const result = await loadBooks(page, currentQuery);
    renderBooks(result.content, true);
    loadMoreBtn.style.display = result.hasMore ? "inline-block" : "none";
});

// Поиск
searchInput.addEventListener("input", async () => {
    const query = searchInput.value.trim().toLowerCase();
    currentQuery = query.length >= 3 ? query : "";
    page = 0;
    const result = await loadBooks(page, currentQuery);
    renderBooks(result.content);
    loadMoreBtn.style.display = result.hasMore ? "inline-block" : "none";
});

// Удаление
tbody.addEventListener("click", async (e) => {
    const link = e.target.closest(".delete-link");
    if (!link) return;
    e.preventDefault();

    if (!confirm("Удалить книгу?")) return;

    const id = link.dataset.id;
    const res = await fetch(`/api/books/${id}`, { method: "DELETE" });
    if (res.ok) link.closest("tr").remove();
    else alert("Ошибка при удалении книги");
});

// Первоначальная загрузка
document.addEventListener("DOMContentLoaded", async () => {
    const result = await loadBooks(page);
    renderBooks(result.content);
    loadMoreBtn.style.display = result.hasMore ? "inline-block" : "none";
});