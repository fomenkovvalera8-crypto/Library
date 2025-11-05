async function fetchSuggestions(url, query = "") {
    try {
        const res = await fetch(`${url}?q=${encodeURIComponent(query)}`);
        if (!res.ok) return [];

        const data = await res.json();

        if (data.content) return data.content;

        return [];
    } catch (e) {
        console.error(e);
        return [];
    }
}


function setupAutocomplete(configs) {
    configs.forEach(cfg => {
        const input = document.getElementById(cfg.inputId);
        const list = document.getElementById(cfg.suggestionsId);
        const hidden = document.getElementById(cfg.hiddenId);

        if (!input || !list || !hidden) return;

        // Делает input только для чтения после выбора
        input.readOnly = false;

        input.addEventListener("input", async () => {
            const q = input.value.trim();
            list.innerHTML = "";
            hidden.value = "";
            input.readOnly = false; // разблокируем на случай новой попытки
            if (q.length < 3) return;

            const items = await fetchSuggestions(cfg.apiUrl, q);
            items.forEach(item => {
                const div = document.createElement("div");
                div.className = "suggestion-item";
                div.textContent = cfg.formatter(item);
                div.onclick = () => {
                    input.value = cfg.formatter(item);
                    input.readOnly = true; // блокируем ввод после выбора
                    hidden.value = item.id;
                    list.innerHTML = "";
                };
                list.appendChild(div);
            });
        });
        input.addEventListener("focus", async () => {
            if (input.readOnly) return;
            list.innerHTML = "";
            const items = await fetchSuggestions(cfg.apiUrl, "");
            items.forEach(item => {
                const div = document.createElement("div");
                div.className = "suggestion-item";
                div.textContent = cfg.formatter(item);
                div.onclick = () => {
                    input.value = cfg.formatter(item);
                    input.readOnly = true;
                    hidden.value = item.id;
                    list.innerHTML = "";
                };
                list.appendChild(div);
            });
        });
        // Добавляем кнопку очистки
        const clearBtn = document.createElement("span");
        clearBtn.textContent = "❌";
        clearBtn.style.marginLeft = "8px";
        clearBtn.style.cursor = "pointer";
        clearBtn.onclick = () => {
            input.value = "";
            input.readOnly = false;
            hidden.value = "";
        };
        input.insertAdjacentElement("afterend", clearBtn);

        document.addEventListener("click", e => {
            if (!list.contains(e.target) && e.target !== input) {
                list.innerHTML = "";
            }
        });
    });
}

// Инициализация автозаполнения для всех полей
setupAutocomplete([
    {
        inputId: "clientSearch-new",
        suggestionsId: "clientSuggestions-new",
        hiddenId: "clientId-new",
        apiUrl: "/api/clients/search",
        formatter: c => `${c.fullName} (${c.birthDate})`
    },
    {
        inputId: "bookSearch-new",
        suggestionsId: "bookSuggestions-new",
        hiddenId: "bookId-new",
        apiUrl: "/api/books/search",
        formatter: b => `${b.title} — ${b.author}`
    },
    {
        inputId: "clientSearch-edit",
        suggestionsId: "clientSuggestions-edit",
        hiddenId: "clientId-edit",
        apiUrl: "/api/clients/search",
        formatter: c => `${c.fullName} (${c.birthDate})`
    },
    {
        inputId: "bookSearch-edit",
        suggestionsId: "bookSuggestions-edit",
        hiddenId: "bookId-edit",
        apiUrl: "/api/books/search",
        formatter: b => `${b.title} — ${b.author}`
    }
]);
document.querySelectorAll("form").forEach(form => {
    form.addEventListener("submit", e => {
        const clientHidden = form.querySelector('input[type="hidden"][id^="clientId"]');
        const bookHidden = form.querySelector('input[type="hidden"][id^="bookId"]');

        if (!clientHidden?.value || !bookHidden?.value) {
            e.preventDefault();
            alert("Пожалуйста, выберите клиента и книгу из списка подсказок.");
        }
    });
});