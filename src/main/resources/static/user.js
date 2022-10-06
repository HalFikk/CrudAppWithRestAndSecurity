const URL = 'http://localhost:8080/api/user'

fetch(URL)
    .then(res => res.json())
    .then(user => {
        navbar.innerHTML = `<b>${user.email}</b> с ролью: ${user.role}`
        userTable.innerHTML =
            `
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.surname}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
        </tr>
        `
    })
