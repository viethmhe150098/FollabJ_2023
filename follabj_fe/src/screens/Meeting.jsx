import React, { useState, useEffect } from 'react';
import Sidebar from "../components/Nav/Sidebar";
import { ZegoUIKitPrebuilt } from '@zegocloud/zego-uikit-prebuilt';

const generateToken = async (tokenServerUrl, userID, channelId) => {
    try {
        const res = await fetch(
            `${tokenServerUrl}/?channelId=${channelId}&userID=${userID}&expired_ts=7200`,
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
    const [roomID, setRoomID] = useState(getUrlParams().get('roomID') || randomID(5));
    const [userID, setUserID] = useState(randomID(5));
    const [userName, setUserName] = useState(randomID(5));
    const [token, setToken] = useState(null);

    useEffect(() => {
        (async () => {
            const tokenData = await generateToken(
                'http://localhost:8080/rtctoken',
                userID,
                roomID
            );
            setToken(tokenData.token);
        })();
    }, [roomID, userID]);

    useEffect(() => {
        if (!token) return;
        const zp = ZegoUIKitPrebuilt.create(
            ZegoUIKitPrebuilt.generateKitTokenForProduction(
                1770178411,
                token,
                roomID,
                userID,
                userName
            )
        );
        zp.joinRoom({
            container: elementRef.current,
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
    }, [token]);

    const elementRef = React.useRef(null);

    return (
            <div
                className="myCallContainer"
                ref={elementRef}
                style={{ width: '100vw', height: '100vh' }}
            ></div>
    );
};

export default Meeting;