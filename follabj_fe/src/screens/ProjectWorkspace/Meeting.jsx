import React from 'react';
import { ZegoUIKitPrebuilt } from '@zegocloud/zego-uikit-prebuilt';

const generateToken = async (tokenServerUrl, userID, channelId) => {
    try {
        const res = await fetch(
            `${tokenServerUrl}/?channelId=${channelId}&userId=${userID}&expired_ts=7200`,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem('access_token'),
                },
            }
        );
        const data = await res.json();
        if (!res.ok) throw new Error(data.message);
        return data;
    } catch (error) {
        console.error(error);
    }
};
function randomID(len) {
    let result = '';
    if (result) return result;
    var chars = '12345qwertyuiopasdfgh67890jklmnbvcxzMNBVCZXASDQWERTYHGFUIOLKJP',
        maxPos = chars.length,
        i;
    len = len || 5;
    for (i = 0; i < len; i++) {
        result += chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return result;
}

const getUrlParams = (url = window.location.href) => {
    const urlStr = url.split('?')[1];
    return new URLSearchParams(urlStr);
};

const Meeting = () => {
    // const [roomID, setRoomID] = useState(getUrlParams().get('roomID') || randomID(5));
    // const [userID, setUserID] = useState(randomID(5));
    // const [userName, setUserName] = useState(randomID(5));
    // const [token, setToken] = useState(null);
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
            userName)

        const zp = ZegoUIKitPrebuilt.create(kitToken);
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
                mode: ZegoUIKitPrebuilt.GroupCall,
            },
        });
        
        }
        
    //const elementRef = React.useRef(myMeeting);

    return (
            <div
                className="myCallContainer"
                ref={myMeeting}
                style={{ width: '100vw', height: '100vh' }}
            >ewqwqe</div>
    );
};

export default Meeting;