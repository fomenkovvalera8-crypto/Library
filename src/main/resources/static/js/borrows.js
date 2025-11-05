let page = 0;
const size = 10;
let currentQuery = '';

const tbody = document.getElementById("borrows-body");
const loadMoreBtn = document.getElementById("loadMoreBtn");
const searchInput = document.getElementById("searchInput");

async function loadBorrows(reset = false) {
    const url = currentQuery
        ? `/api/borrows/search?q=${encodeURIComponent(currentQuery)}&page=${page}&size=${size}`
        : `/api/borrows/page?page=${page}&size=${size}`;

    const response = await fetch(url);
    const pageData = await response.json();

    const newBorrows = pageData.content;
    const hasMore = pageData.hasMore;

    if (reset) tbody.innerHTML = "";

    function formatDate(dateStr) {
        if (!dateStr) return '-';
        const date = new Date(dateStr);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const year = date.getFullYear();
        return `${day}.${month}.${year}`;
    }

    newBorrows.forEach(b => {
        const row = document.createElement("tr");
        row.innerHTML = `
        <td><a href="/borrows/edit/${b.id}" title="Редактировать">🔃</a></td>
        <td>${b.client?.fullName ?? 'Не назначен'}</td>
        <td>${formatDate(b.client?.birthDate)}</td>
        <td>${b.book?.title ?? '-'}</td>
        <td>${b.book?.author ?? '-'}</td>
        <td>${b.book?.isbn ?? '-'}</td>
        <td>${formatDate(b.borrowDate)}</td>
        <td><a href="#" class="delete-link" data-id="${b.id}" title="Удалить">❌</a></td>
    `;
        tbody.appendChild(row);
    });

    loadMoreBtn.style.display = hasMore ? "block" : "none";
}

loadMoreBtn?.addEventListener("click", () => {
    page++;
    loadBorrows();
});

searchInput.addEventListener("input", () => {
    const filter = searchInput.value.trim();
    if (filter.length < 3) currentQuery = '';
    else currentQuery = filter;
    page = 0;
    loadBorrows(true);
});
tbody.addEventListener("click", async (e) => {
    const link = e.target.closest(".delete-link");
    if (!link) return;

    e.preventDefault();
    const id = link.dataset.id;

    if (!confirm("Удалить запись?")) return;

    const response = await fetch(`/api/borrows/${id}`, { method: "DELETE" });

    if (response.ok) {
        // Удаляем строку из DOM
        link.closest("tr").remove();
    } else {
        alert("Ошибка при удалении записи");
    }
});