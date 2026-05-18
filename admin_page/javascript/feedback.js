/* SIDEBAR */
function toggleSidebar(){
    document.querySelector(".sidebar").classList.toggle("open");
}

/* DATA */
let originalData = [
{name:"Rahul",rating:5,comment:"Excellent service",reason:"Technician arrived on time and fixed issue quickly."},
{name:"Priya",rating:4,comment:"Good experience",reason:"Fast service but slight delay in response."},
{name:"Amit",rating:3,comment:"Average",reason:"Resolved but took longer than expected."},
{name:"Sneha",rating:5,comment:"Very satisfied",reason:"Professional and smooth service."},
{name:"Raj",rating:2,comment:"Slow service",reason:"Technician arrived late."},
{name:"Neha",rating:4,comment:"Good support",reason:"Helpful support team."},
{name:"Vikas",rating:1,comment:"Very bad",reason:"Issue not resolved properly."},
{name:"Anjali",rating:5,comment:"Amazing",reason:"Quick and efficient service."},
{name:"Karan",rating:3,comment:"Okay",reason:"Acceptable but not fast."},
{name:"Pooja",rating:4,comment:"Nice staff",reason:"Friendly behavior."},
{name:"Rohit",rating:2,comment:"Delayed",reason:"Scheduling issues."},
{name:"Meena",rating:5,comment:"Perfect",reason:"Completed on time."},
{name:"Arjun",rating:4,comment:"Good",reason:"Satisfied overall."},
{name:"Simran",rating:3,comment:"Needs improvement",reason:"Slow process."},
{name:"Dev",rating:5,comment:"Recommended",reason:"Excellent experience."},
{name:"Suresh", rating:4, comment:"Prompt service", reason:"Issue was resolved within the same day."},
{name:"Kavita", rating:5, comment:"Outstanding", reason:"Very courteous technician and fast resolution."},
{name:"Manoj", rating:2, comment:"Unsatisfactory", reason:"Problem reoccurred after few days."},
{name:"Nikita", rating:3, comment:"Decent", reason:"Service was okay but communication could improve."},
{name:"Alok", rating:4, comment:"Reliable", reason:"Technician explained the issue clearly."}
];

let feedbackData = [...originalData];

/* PAGINATION */
let currentPage = 1;
let rowsPerPage = 10;

/* CHART */
let ratings = [0,0,0,0,0];

let ctx = document.getElementById("chart").getContext("2d");

let chart = new Chart(ctx,{
    type:"bar",
    data:{
        labels:["1","2","3","4","5"],
        datasets:[{
            label:"Customer Satisfaction",
            data:ratings,
            backgroundColor:["#4A90E2","#4A90E2","#4A90E2","#2C5FCD","#2C5FCD"],
            borderRadius:8
        }]
    },
    options:{
        responsive:true,
        plugins:{ legend:{ display:true }},
        scales:{
            x:{ title:{ display:true, text:"Rating (1 to 5)" }},
            y:{
                beginAtZero:true,
                title:{ display:true, text:"Number of Customers" },
                ticks:{ stepSize:1 }
            }
        }
    }
});

/* LOAD TABLE */
function loadData(){
    let table = document.getElementById("feedbackTable");
    table.innerHTML = "";

    let start = (currentPage - 1) * rowsPerPage;
    let end = start + rowsPerPage;

    let pageData = feedbackData.slice(start, end);

    let html = "";
    pageData.forEach(f=>{
        html += `
        <tr>
            <td>${f.name}</td>
            <td>${f.rating}</td>
            <td>${f.comment}</td>
            <td>${f.reason}</td>
        </tr>`;
    });

    table.innerHTML = html;

    createPagination();
    updateChart(); 
}

/* PAGINATION */
function createPagination(){
    let old = document.getElementById("pagination");
    if(old) old.remove();
    let pageCount = Math.ceil(feedbackData.length / rowsPerPage);
    let div = document.createElement("div");
    div.id = "pagination";
    div.style.display = "flex";
    div.style.justifyContent = "center";
    div.style.gap = "8px";
    div.style.margin = "20px 0";

    function btn(text,page){
        let b = document.createElement("button");
        b.innerText = text;
        b.style.padding = "8px 14px";
        b.style.borderRadius = "8px";
        b.style.cursor = "pointer";
        if(page === currentPage){
            b.style.background="#2f5fcd";
            b.style.color="white";
        }
        b.onclick=()=>{
            currentPage=page;
            loadData();
        };
        return b;
    }

    if(currentPage>1) div.appendChild(btn("Prev",currentPage-1));
    let start = Math.max(1,currentPage-1);
    let end = Math.min(start+2,Math.ceil(feedbackData.length/rowsPerPage));
    if(end-start<2) start=Math.max(1,end-2);

    for(let i=start;i<=end;i++) div.appendChild(btn(i,i));

    if(currentPage<Math.ceil(feedbackData.length/rowsPerPage))
        div.appendChild(btn("Next",currentPage+1));

    document.querySelector(".table-box").appendChild(div);
}

/*FILTER */
function filterTable(){
    let value = document.getElementById("ratingFilter").value;
    if(value==="all"){
        feedbackData=[...originalData];
    }else{
        feedbackData = originalData.filter(f=>f.rating == value);
    }
    currentPage=1;
    loadData();
}

/* SORTING FUNCTION */
function sortData(type){
    if(type==="high"){
        feedbackData.sort((a,b)=>b.rating-a.rating);
    }
    else if(type==="low"){
        feedbackData.sort((a,b)=>a.rating-b.rating);
    }
    else if(type==="quality"){
        // Excellent > Good > Average > Bad
        let order = {5:1,4:2,3:3,2:4,1:5};
        feedbackData.sort((a,b)=>order[a.rating]-order[b.rating]);
    }
    currentPage=1;
    loadData();
}
/* FIXED CHART */
function updateChart(){
    ratings = [0,0,0,0,0];
    originalData.forEach(f=>{
        ratings[f.rating-1]++;
    });
    chart.data.datasets[0].data = ratings;
    chart.update();
}

/* INIT */
window.onload = loadData;
/* NAV */
function goNextPage() {
    window.location.href = "admin_side.html";
}
/* LOGOUT */
function openLogoutPopup() {
    document.getElementById("logoutPopup").classList.add("show");
}
function closeLogoutPopup() {
    document.getElementById("logoutPopup").classList.remove("show");
}
function confirmLogout() {
    alert("Logged out successfully");
    window.location.href = "adminLogin.html";
}
/* OUTSIDE CLICK */
window.onclick = function(e) {
    const popup = document.getElementById("logoutPopup");
    if (e.target === popup) popup.classList.remove("show");
};