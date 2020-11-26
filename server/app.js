var admin = require("firebase-admin");
var serviceAccount = require("./push-fcm-66308-firebase-adminsdk-i689h-b1a9f32a62.json");
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://push-fcm-66308.firebaseio.com"
});

//pushUser("eqZ-W-26S_yve-YbUuj__4:APA91bHT-_EWejf3rafKfzZ-8aDMVHY5oFWTjW6sXd0Fll6ACAZ3vy51oO_GoaykyY6t7A0LbCRuGab2bbZGo97_1nFixWQO6toFtQEu8qJmEZSfSnks3HhR2aUY0MxueYxtGuEMhBr7")
//pushUser("exD1EKlnThyHnQL5d07Sxr:APA91bF2PAjsWr8WZ86TRa0zGVJ3MPpWsWX0qnObakC8MEh4AP4OauTRZx7bz1UUObhmKMgkdKNLF7Rhx1rWTlO0YiqvYSFHOhQsTzbw4c3SzhQgg8ebvZT-glo-8df0JfK3rMk6Jb4d")
pushUser("cnuIUnFTSHO4z2xX_HJuEL:APA91bHdaUMMShIi10sDYKbERWYrZAPS4OazTadHNkDB1J4WI215bTP5W1jC3kCHX9glYMwZQQ7CReKa7QMkTv1qg0phYyPqIgJA4EKEsD2ParrBv-AFgBmRANTSXmsiW5tuJLF7DHwS")
console.log("Send...")

function pushUser(token) {
    try {
        var payload = {
            data: {
                title: "Title",
                message: "Message"
            }
        };

        // var payload = {
        //     notification: {
        //         title: "You have 1 new subscribe.",
        //         body: "user"
        //     }
        // };

        var options = {
            priority: "high",
            timeToLive: 60 * 60 * 24
        }
        admin.messaging().sendToDevice(token, payload, options)
            .then(res => {
                console.log("<---NOTIFY---> OK");
            })
            .catch(err => {
                console.log("<---NOTIFY---> Error");
            });
    } catch (err) {
        console.log("<---EX---> " + err);
    }
}