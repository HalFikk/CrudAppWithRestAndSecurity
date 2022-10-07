const URL = 'http://localhost:8080/api/admin'
let output
const deleteButton = document.querySelector('.btn-danger')
const editButton = document.querySelector('.btn-primary')
let editButtonIsPressed = false
let delButtonIsPressed = false
let children
let parent
let id

const tableOfUser = (user) => {
    adminTable.innerHTML += `
    <tr>
        <td>${user.id}</td>
        <td><p>${user.name}</p></td>
        <td>${user.surname}</td>
        <td>${user.age}</td>
        <td>${user.email}</td>
        <td>${user.role}</td>
        <td><button type="button" class="btn btn-info" data-toggle="modal" id="edit"
                data-id=${user.id} data-target="#modalEdit"> Редактировать</button></td>
        <td><button type="button" class="btn btn-danger" data-toggle="modal" id="delete"
                data-id=${user.id} data-target="#modalDelete"> Удалить</button></td>
    </tr>
    `
}

const tableOfUsers = (user) => {
    output += `
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.surname}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
            <td><button type="button" class="btn btn-info" data-toggle="modal" id="edit"
                data-id=${user.id} data-target="#modalEdit"> Редактировать</button></td>
            <td><button type="button" class="btn btn-danger" data-toggle="modal" id="delete"
                    data-id=${user.id} data-target="#modalDelete"> Удалить</button></td>
        </tr>
        `
}

fetch(URL + '/page')
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

fetch(URL + '/users')
    .then(res => res.json())
    .then(user => {
        user.forEach(user => {
            tableOfUsers(user)
        })
        adminTable.innerHTML = output
    })

addUserForm.addEventListener('submit', (e) => {
    e.preventDefault()
    fetch(URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: newName.value,
            surname: newSurname.value,
            age: newAge.value,
            email: newEmail.value,
            password: newPassword.value,
            role: newUserRole.value
        })
    })
        .then(res => res.json())
        .then(data => {
            tableOfUser(data)
        })
        .then(addUserForm.reset())
    usersTable.click()
})

adminTable.addEventListener('click', (e) => {
    e.preventDefault()
    editButtonIsPressed = e.target.id == 'edit'
    delButtonIsPressed = e.target.id == 'delete'
    id = e.target.dataset.id
    parent = e.target.closest('tr')
    children = parent.children

    if (delButtonIsPressed) {
        deleteId.value = children[0].textContent
        deleteName.value = children[1].textContent
        deleteSurname.value = children[2].textContent
        deleteAge.value = children[3].textContent
        deleteEmail.value = children[4].textContent
        deleteRoles.value = children[5].textContent
    }

    if (editButtonIsPressed) {
        editId.value = children[0].textContent
        editName.value = children[1].textContent
        editSurname.value = children[2].textContent
        editAge.value = children[3].textContent
        editEmail.value = children[4].textContent
        editRoles.value = children[5].textContent
    }
})

deleteButton.addEventListener('click', (e) => {
    e.preventDefault()
    fetch(`${URL}/${id}`, {
        method: 'DELETE'
    })
    parent.innerHTML = ''
})

editButton.addEventListener('click', (e) => {
    e.preventDefault()
    fetch(`${URL}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: editId.value,
            name: editName.value,
            surname: editSurname.value,
            age: editAge.value,
            email: editEmail.value,
            password: editPassword.value,
            role: editRoles.value
        })
    })
    children[1].textContent = editName.value
    children[2].textContent = editSurname.value
    children[3].textContent = editAge.value
    children[4].textContent = editEmail.value
    children[5].textContent = editRoles.value
})
