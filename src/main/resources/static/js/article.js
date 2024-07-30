const modifyBtn = document.getElementById('modify-btn');

if(modifyBtn){

    modifyBtn.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        const id = document.getElementById('article-id').value;

        const url = `/api/articles/${id}`;
        fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        }).then(() => {
            alert('수정이 완료되었습니다.');
            location.replace('/article/'+id) ;
        });
    });
}

const deleteBtn = document.getElementById('delete-btn');

if(deleteBtn){

    deleteBtn.addEventListener('click', event => {
        event.preventDefault();
        const id = document.getElementById('article-id').value;
        const url = `/api/articles/${id}`;
        fetch(url, {
            method: 'DELETE'
        }).then(() => {
            alert('삭제가 완료되었습니다.');
            location.replace('/articleList') ;
        });
    });
}

const createBtn = document.getElementById('create-btn');

if(createBtn){

    createBtn.addEventListener('click', event => {
        const url = `/api/articles`;
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        }).then(() => {
            alert('등록이 완료되었습니다.');
            location.replace('/articleList') ;
        });
    });
}