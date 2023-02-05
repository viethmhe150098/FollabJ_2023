import Sidebar from "../components/Nav/Sidebar";

function generateToken(tokenServerUrl, userID, channelId) {
    // Obtain the token interface provided by the App Server
    return fetch(
        `${tokenServerUrl}/?channelId=${channelId}&userId=${userID}&expired_ts=7200`,
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': 'Bearer '+localStorage.getItem('access_token')
            }
        }
    ).then((res) => {
        if (res.status == 200) {
            return res.json()
        } else {
            return new Error("Failed to fetch comment: " + res.statusText);
        }
    });
}

export function getUrlParams(
    url = window.location.href
) {
    let urlStr = url.split('?')[1];
    return new URLSearchParams(urlStr);
}

const Meeting = () => {
    const roomID = getUrlParams().get('roomID') || randomID(5);
    const userID = randomID(5);
    const userName = randomID(5);
    let myMeeting = async (element) => {
        // generate token
        const token = await generateToken(
            'http://localhost:8080/rtctoken',
            userID,
            roomID
        );
        console.log(token.token)
        const kitToken = ZegoUIKitPrebuilt.generateKitTokenForProduction(
            1770178411,
            token.token,
            roomID,
            userID,
            userName
        );
        // create instance object from token
        const zp = ZegoUIKitPrebuilt.create(kitToken);
        // start the call
        zp.joinRoom({
            container: element,
            sharedLinks: [
                {
                    name: 'Personal link',
                    url:
                        window.location.origin +
                        window.location.pathname +
                        '?roomID=' +
                        roomID,
                },
            ],
            scenario: {
                mode: ZegoUIKitPrebuilt.GroupCall, // To implement 1-on-1 calls, modify the parameter here to [ZegoUIKitPrebuilt.OneONoneCall].
            },
        });
    };
    return (
        <Sidebar>
            <div
                className="myCallContainer"
                ref={myMeeting}
                style={{ width: '100vw', height: '100vh' }}
            ></div>
        </Sidebar>
    )
}