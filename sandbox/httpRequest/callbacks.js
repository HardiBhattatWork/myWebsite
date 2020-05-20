const posts = [
    {title: 'Post One', body: "This is post One"},
    {title: 'Post Two', body: "This is post Two"}
];

function getPosts(){
    setTimeout(() => {
        let output = "";
        posts.forEach((post, index) => {
            output += `<li>${post.title}</li>`;
        });
        document.body.innerHTML = output;
    }, 1000);
}

function createPosts(post, callback) {
    setTimeout(() => {
        posts.push(post);
        callback();
    }, 2000)
}

createPosts({title: "Post Three", body:"This is Post Three"}, getPosts);