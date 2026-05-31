import asyncio
import json

import websockets


async def main():
    async with websockets.connect("ws://localhost:8080/ws/code") as socket:
        await socket.send(json.dumps({"projectId": "demo", "path": "src/auth.c", "delta": "return 0;"}))
        print("sent websocket delta")


if __name__ == "__main__":
    asyncio.run(main())
