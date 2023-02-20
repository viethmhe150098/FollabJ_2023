import React, { useEffect } from "react";

import { DragDropContext, Draggable, Droppable } from "react-beautiful-dnd";
import styled from "styled-components";
import Popup from "reactjs-popup";
import Content from "./AddTask";
import { connect } from 'react-redux'
// import { ShowTasksApi} from "../../Redux/actions"


const Kanban = ({tasks, ShowTasksApi}) => {
    
    useEffect(() => {
        ShowTasksApi()
    }, [])

    //console.log(data)
    const onDragEnd = result => {
        console.log(result);

        if (!result.destination) return;
        const { source, destination } = result;

        //move to task to other column
        if (source.droppableId != destination.droppableId) {
            const sourceColIndex = tasks.findIndex(e => e.id == source.droppableId)
            const destinationColIndex = tasks.findIndex(e => e.id == destination.droppableId)

            const sourceCol = tasks[sourceColIndex]
            const destinationCol = tasks[destinationColIndex]
            
            // console.log(sourceCol)
            // console.log(destinationCol)

            const sourceTasks = [...sourceCol.tasks]
            const destinationTasks = [...destinationCol.tasks]

            const [removed] = sourceTasks.splice(source.index, 1)
            destinationTasks.splice(destination.index, 0, removed)

            tasks[sourceColIndex].tasks = sourceTasks
            tasks[destinationColIndex].tasks = destinationTasks


        } else { //move task in the one column
            const columnIndex = tasks.findIndex(e => e.id == destination.droppableId);

            const column = tasks[columnIndex]

            const columnTasks = [...column.tasks]
            //console.log(columnTasks)

            const [removed] = columnTasks.splice(source.index, 1)
            //tasks.splice(destination.index, 0, removed)
            columnTasks.splice(destination.index, 0, removed)
            //console.log(columnTasks)

            tasks[columnIndex].columnTasks = columnTasks
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
                        tasks.map(section => (
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

const mapStateToProps = ({tasks}) => {
    return {
        tasks
    }
}
export default Kanban
// export default connect(mapStateToProps, {ShowTasksApi})(Kanban)

const Wrapper = styled.section`
  color: black;
  font-family: 'Lato', sans-serif;
  line-height: 1.5;
  width: 80%;
  overflow: auto
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