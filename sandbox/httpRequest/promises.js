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

function createPosts(post) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            posts.push(post);

            const error = false;

            if(!error) {
                resolve();
            } else {
                reject('Error: Something went wrong');
            }
        }, 2000);
    });
}

// createPosts({title: "Post Three", body:"This is Post Three"})
//     .then(getPosts)
//     .catch(err => console.log(err));

// Async / Await
// async function init() {
//     await createPosts({title: "Post Three", body:"This is Post Three"});

//     getPosts();
// }

// init();

//Async / Await / Fetch
async function fetchUsers() {
    const res = await fetch('https://jsonplaceholder.typicode.com/users');

    const data = await res.json();

    console.log(data);
}

fetchUsers();

// Promise.all
// const promise1 = Promise.resolve("Hello World");
// const promise2 = 10;
// const promise3 = new Promise(function (resolve, reject) {
//     setTimeout(resolve, 2000, "GoodBye"); 
// });
// const promise4 = fetch('https://jsonplaceholder.typicode.com/users').then(res => res.json());

// Promise.all([promise1, promise2, promise3, promise4]).then(values => console.log(values));

/*
function first(callback) {
    setTimeout(function() {
        callback(1);
    }, 1000);
}

function second(callback) {
    setTimeout(function() {
        callback(2);
    }, 2500);
}

function firstPromise(post){
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            console.log(firstPromise);
            resolve(1);
        })
    }, 1000);
}

function secondPromise(post){
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            console.log(secondPromise);
            resolve(2);
        })
    }, 2500);
};

Promise.all([firstPromise(), secondPromise()]).then(function(value){
    let myFirstPromise = value[0];
    let mySecondPromise = value[1];
    console.log(myFirstPromise, mySecondPromise); 
});
*/

