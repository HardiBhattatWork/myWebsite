/*
Undirected
Not Weighted
No Cycles
*/

// DATA
const airports = 'PHX BKK OKC JFK LAX MEX EZE HEL LOS LAP LIM'.split(' ');

const routes = [
    ['PHX', 'LAX'],
    ['PHX', 'JFK'],
    ['JFK', 'OKC'],
    ['JFK', 'HEL'],
    ['JFK', 'LOS'],
    ['MEX', 'LAX'],
    ['MEX', 'BKK'],
    ['MEX', 'LIM'],
    ['MEX', 'EZE'],
    ['LIM', 'BKK'],
];


// The graph
const adjacencyList = new Map();

// Add node
function addNode(airport) {
    // console.log(airport);
    adjacencyList.set(airport, []);
}

// Add edge, undirected
function addEdge(origin, destination) {
    // console.log(origin, destination);
    adjacencyList.get(origin).push(destination);
    adjacencyList.get(destination).push(origin);
}

// Create the Graph
airports.forEach(addNode);
routes.forEach(function(route){ addEdge(...route); /*console.log(...route)*/})

console.log(adjacencyList);

/*
Breadth-first Search (BFS) starts by pushing all of the direct children to a queue (first-in, first-out). It then visits each item in queue and adds the next layer of children to the back of the queue. This example uses a Set to keep track of nodes that have already been visited.
*/
function bfs(start) {

    const visited = new Set();

    const queue = [start]


    while (queue.length > 0) {

        const airport = queue.shift(); // mutates the queue

        const destinations = adjacencyList.get(airport);


        for (const destination of destinations) {
;

            if (destination === 'BKK')  {
                console.log(`BFS found Bangkok!`)
            }

            if (!visited.has(destination)) {
                visited.add(destination);
                queue.push(destination);
                console.log(destination);
            }
           
        }

        
    }

}

bfs('PHX')

console.log("Start DFS");
/* 
Depth-first Search (DFS) will go as far into the graph as possible until it reaches a node without any children, at which point it backtracks and continues the process. The algorithm can be implemented with a recursive function that keeps track of previously visited nodes. If a node has not been visited, we call the function recursively.
*/
function dfs(start, visited = new Set()) {

    console.log(start)
    
    visited.add(start);

    const destinations = adjacencyList.get(start);

    for (const destination of destinations) {

        if (destination === 'BKK') { 
            console.log(`DFS found Bangkok`)
            return;
        }
        
        if (!visited.has(destination)) {
            dfs(destination, visited);
        }

    }

}

dfs('PHX')