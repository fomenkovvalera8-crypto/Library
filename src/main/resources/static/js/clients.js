let page = 0;
const size = 10;
let currentQuery = '';

const tbody = document.getElementById("clients-body");
const loadMoreBtn = document.getElementById("loadMoreBtn");
const searchInput = document.getElementById("searchInput");

function formatDate(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}.${month}.${year}`;
}

async function loadClients(reset = false) {
    const url = currentQuery
        ? `/api/clients/search?q=${encodeURIComponent(currentQuery)}&page=${page}&size=${size}`
        : `/api/clients/page?page=${page}&size=${size}`;

    const response = await fetch(url);
    const data = await response.json();
    const newClients = data.content;

    if (reset) tbody.innerHTML = "";

    newClients.forEach(c => {
        const row = document.createElement("tr");
        row.innerHTML = `
                <td><a href="/clients/edit/${c.id}" class="icon-link">🔃</a></td>
                <td>${c.fullName}</td>
                <td>${formatDate(c.birthDate)}</td>
                <td><a href="#" class="delete-link" data-id="${c.id}" title="Удалить">❌</a></td>
            `;
        tbody.appendChild(row);
    });

    if (!data.hasMore || newClients.length === 0) loadMoreBtn.style.display = "none";
    else loadMoreBtn.style.display = "block";
}

loadMoreBtn?.addEventListener("click", () => {
    page++;
    loadClients();
});

searchInput.addEventListener("input", () => {
    const filter = searchInput.value.trim();
    if (filter.length < 3) {
        currentQuery = '';
    } else {
        currentQuery = filter;
    }
    page = 0;
    loadClients(true);
});

tbody.addEventListener("click", async (e) => {
    const link = e.target.closest(".delete-link");
    if (!link) return;

    e.preventDefault();
    const id = link.dataset.id;

    if (!confirm("Удалить клиента?")) return;

    const response = await fetch(`/api/clients/${id}`, { method: "DELETE" });

    if (response.ok) {
        link.closest("tr").remove();
    } else {
        alert("Ошибка при удалении клиента");
    }
});