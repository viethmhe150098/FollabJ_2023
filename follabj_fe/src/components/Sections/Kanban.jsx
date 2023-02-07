import React, { useEffect } from "react";
import { useState } from "react";
import { v4 as uuid } from "uuid";
import { DragDropContext, Draggable, Droppable } from "react-beautiful-dnd";
import styled from "styled-components";
import Popup from "reactjs-popup";
import Content from "../Sections/Modal";

// function fetchData() {
//     // Obtain the token interface provided by the App Server
//     return fetch(
//       "http://localhost:8080/task",
//       {
//         method: 'GET',
//         headers: {
//             'Content-Type': 'application/json',
//             'Accept': 'application/json',
//             'Authorization': 'Bearer ' + localStorage.getItem('access_token'),
//         },
//       }
//     ).then((res) => {
//       if(res.status == 200){
//         return res.json()
//       }else{
//         return new Error("Failed to fetch comment: " + res.statusText);
//       }
//     });
//   }

const mockData = [
    {
        id: uuid(),
        title: 'To do',
        tasks: [
            {
                id: uuid(),
                title: 'create report 4',
                assignee: 'John'
            }
        ]
    },
    {
        id: uuid(),
        title: 'In progress',
        tasks: [
            {
                id: uuid(),
                title: 'Developing frontend',
                assignee: 'John'
            },
            {
                id: uuid(),
                title: 'Creating report 2',
                assignee: 'Bill'
            },
            {
                id: uuid(),
                title: 'Creating report 3',
                assignee: 'Zack'
            },
        ]
    },
    {
        id: uuid(),
        title: 'Done',
        tasks: [
        ]
    }
];

export default function Kanban() {
    const [data, setData] = useState([]);
    useEffect(() => {
        const fetchData = async () => {
            const result = await fetch(
                "http://localhost:8080/task",
                {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('access_token'),
                    },
                }

            );
            
            //console.log(result)

            const json = await result.json();
            //console.log(json)
            setData(json.list)
        }

        fetchData()
    }, [])

    //console.log(data)
    const onDragEnd = result => {
        console.log(result);

        if (!result.destination) return;
        const { source, destination } = result;

        //move to task to other column
        if (source.droppableId !== destination.droppableId) {
            const sourceColIndex = data.findIndex(e => e.id === source.droppableId)
            const destinationColIndex = data.findIndex(e => e.id === destination.droppableId)

            const sourceCol = data[sourceColIndex]
            const destinationCol = data[destinationColIndex]

            const sourceTasks = [...sourceCol.tasks]
            const destinationTasks = [...destinationCol.tasks]

            const [removed] = sourceTasks.splice(source.index, 1)
            destinationTasks.splice(destination.index, 0, removed)

            data[sourceColIndex].tasks = sourceTasks
            data[destinationColIndex].tasks = destinationTasks


            setData(data)
        } else { //move task in the one column
            const columnIndex = data.findIndex(e => e.id === destination.droppableId);

            const column = data[columnIndex]

            const tasks = [...column.tasks]
            console.log(tasks)

            const [removed] = tasks.splice(source.index, 1)
            tasks.splice(destination.index, 0, removed)
            tasks.splice(destination.index, 0, removed)
            console.log(tasks)

            data[columnIndex].tasks = tasks

            setData(data)
        }

    }

    return (
        <Wrapper id='tasks'>
            <HeaderInfo>
                <h1 className="font40 extraBold">Kanban Board</h1>
            </HeaderInfo>
            <DragDropContext onDragEnd={onDragEnd}>
                <KanbanBoard >
                    {
                        data.map(section => (
                            <Droppable
                                key={String(section.id)}
                                droppableId={String(section.id)}
                            >
                                {(provided) => (
                                    <BoardSection
                                        ref={provided.innerRef}
                                        {...provided.droppableProps}
                                    >
                                        <SectionTitle>{section.title}</SectionTitle>
                                        <Popup modal trigger={<Card style={{ cursor: "pointer" }}><p className="extraBold" style={{ color: "#434242" }}>+ Add issue</p></Card>}>
                                            {close => <Content close={close} />}
                                        </Popup>
                                        <SectionContent>
                                            {
                                                section.tasks.map((task, index) => (
                                                    <Draggable
                                                        key={String(task.id)}
                                                        draggableId={String(task.id)}
                                                        index={index}
                                                    >
                                                        {(provided, snapshot) => (
                                                            <div
                                                                ref={provided.innerRef}
                                                                {...provided.draggableProps}
                                                                {...provided.dragHandleProps}
                                                                style={{
                                                                    ...provided.draggableProps.style,
                                                                    opacity: snapshot.isDragging ? '0.5' : '1'
                                                                }}
                                                            >
                                                                <Card>
                                                                    <div>{task.title}</div>
                                                                    <div>{task.assignee}</div>
                                                                </Card>
                                                            </div>
                                                        )}
                                                    </Draggable>
                                                ))
                                            }
                                            {provided.placeholder}
                                        </SectionContent>
                                    </BoardSection>
                                )}
                            </Droppable>
                        ))
                    }
                </KanbanBoard>
            </DragDropContext>
        </Wrapper>
    )
}

const Wrapper = styled.section`
  color: black;
  font-family: 'Lato', sans-serif;
  line-height: 1.5;
`;

const KanbanBoard = styled.div`
    display: flex;
    align-items: flex-start;
`

const BoardSection = styled.div`
    width: 400px;
    background-color: #F5B17B;
    padding: 10px;
    border-radius: 10px;
    margin: 10px;
    box-shadow:  5px 5px 5px #ded5c1;
`

const SectionTitle = styled.div`
    font-size: 1.2rem;
    margin: 10px 0 20px;
    color: #434242;
    font-weight: bold;

`

const SectionContent = styled.div`
    margin-top: 10px;
`

const Card = styled.div`
    padding: 30px;
    color: #716F81;
    background-color: #F2EEE5;
    border-radius: 10px;
    margin-top: 10px;
`
const HeaderInfo = styled.div`
  margin-bottom: 30px;
  margin-left: 5px;
  @media (max-width: 860px) {
    text-align: center;
  }
`;