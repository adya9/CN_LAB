// const APP_ID = "2dc4431cf75c4faca25678e452b86416"
// const TOKEN = "007eJxTYLhWsfPejqs9xrLaJeFeb0UmOlUybbr4drrOvS+WlbkdoiYKDEYpySYmxobJaeamySZpicmJRqZm5hapJqZGSRZmJoZmjgdPpzQEMjI4qqcxMjJAIIjPwpCbmJnHwAAAYW8e9w=="
// const CHANNEL = "main"
// const APP_ID = "5a11d378dc674f23a12e5e2e3da5b4b9"
// const TOKEN = "007eJxTYDh852d/vBd3aOKcfs6jNxzsZvBYTLE4MHH2Zpb83Cd79m5XYDBNNDRMMTa3SEk2MzdJMzJONDRKNU01SjVOSTRNMkmyjLh0OqUhkJEhO7mAmZEBAkF8FobcxMw8BgYAeiQgMA=="
// const CHANNEL = "main"
const APP_ID = "6834e6fe4cb7470cab7d3061216da35d"
const TOKEN = "007eJxTYJj7bYECE3e56Tc71pS49dMOJ9zcGXLbbvp2zWMbd/w+cfeJAoOZhbFJqllaqklykrmJuUFyYpJ5irGBmaGRoVlKorFpyozmGykNgYwMIbOCGRkZIBDEZ2HITczMY2AAANAOIX4="
const CHANNEL = "main"


// Agora client to manage communication with agora platform 
const client = AgoraRTC.createClient({mode:'rtc', codec:'vp8'})

let localTracks = []   //for storing the local audio video 
let remoteUsers = {}   //for storing the list of remote users


//this function is used to join the videocall and displays the local user audio and video 
let joinAndDisplayLocalStream = async () => {  

    client.on('user-published', handleUserJoined)
    
    client.on('user-left', handleUserLeft)
    
    let UID = await client.join(APP_ID, CHANNEL, TOKEN, null)

    localTracks = await AgoraRTC.createMicrophoneAndCameraTracks() 

    let player = `<div class="video-container" id="user-container-${UID}">
                        <div class="video-player" id="user-${UID}"></div>
                  </div>`
    document.getElementById('video-streams').insertAdjacentHTML('beforeend', player)

    localTracks[1].play(`user-${UID}`)
    
    await client.publish([localTracks[0], localTracks[1]])
}

let joinStream = async () => {
    await joinAndDisplayLocalStream()
    document.getElementById('join-btn').style.display = 'none'
    document.getElementById('stream-controls').style.display = 'flex'
}

let handleUserJoined = async (user, mediaType) => {
    remoteUsers[user.uid] = user 
    await client.subscribe(user, mediaType)

    if (mediaType === 'video'){
        let player = document.getElementById(`user-container-${user.uid}`)
        if (player != null){
            player.remove()
        }

        player = `<div class="video-container" id="user-container-${user.uid}">
                        <div class="video-player" id="user-${user.uid}"></div> 
                 </div>`
        document.getElementById('video-streams').insertAdjacentHTML('beforeend', player)

        user.videoTrack.play(`user-${user.uid}`)
    }

    if (mediaType === 'audio'){
        user.audioTrack.play()
    }
}

//this function removes the particular web container from the web page 
let handleUserLeft = async (user) => {
    delete remoteUsers[user.uid]
    document.getElementById(`user-container-${user.uid}`).remove()
}


let leaveAndRemoveLocalStream = async () => {
    for(let i = 0; localTracks.length > i; i++){
        localTracks[i].stop()
        localTracks[i].close()
    }

    await client.leave()
    document.getElementById('join-btn').style.display = 'block'
    document.getElementById('stream-controls').style.display = 'none'
    document.getElementById('video-streams').innerHTML = ''
}

let toggleMic = async (e) => {
    if (localTracks[0].muted){
        await localTracks[0].setMuted(false)
        e.target.innerText = 'Mic on'
        e.target.style.backgroundColor = 'cadetblue'
    }else{
        await localTracks[0].setMuted(true)
        e.target.innerText = 'Mic off'
        e.target.style.backgroundColor = '#EE4B2B'
    }
}

let toggleCamera = async (e) => {
    if(localTracks[1].muted){
        await localTracks[1].setMuted(false)
        e.target.innerText = 'Camera on'
        e.target.style.backgroundColor = 'cadetblue'
    }else{
        await localTracks[1].setMuted(true)
        e.target.innerText = 'Camera off'
        e.target.style.backgroundColor = '#EE4B2B'
    }
}

document.getElementById('join-btn').addEventListener('click', joinStream)
document.getElementById('leave-btn').addEventListener('click', leaveAndRemoveLocalStream)
document.getElementById('mic-btn').addEventListener('click', toggleMic)
document.getElementById('camera-btn').addEventListener('click', toggleCamera)