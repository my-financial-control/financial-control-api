db = db.getSiblingDB('admin');
db.createUser({
    user: "app",
    pwd: "pass",
    roles: [{ role: "readWrite", db: "financialcontrol" }]
});
