let mySet = new Set();
let myArray = new Array(1,1,2,3,3,4,5,6);

myArray.forEach(val =>{
    mySet.add(val);
});

console.log(mySet);