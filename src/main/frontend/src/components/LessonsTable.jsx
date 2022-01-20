import React from 'react';
import {useHistory} from "react-router-dom";
import DeleteModal from "./DeleteModal";


const LessonsTable = ({lessons}) => {
    const router = useHistory();
    let descriptionMaxLength = 500;

    return (
        <div>
            <table className="table table-hover">
                <thead>
                <tr>
                    <th className="col-sm-1" scope="col">Title</th>
                    <th scope="col">Description</th>
                    <th className="col-sm-1" scope="col-">Access</th>
                    <th className="col-sm-1" scope="col-">Author</th>
                    <th className="col-sm-2" scope="col" colSpan="4"/>
                </tr>
                </thead>
                <tbody>
                {lessons.map((lesson) => {
                    return (
                        <tr key={lesson.id}>
                            <td scope="row">{lesson.title}</td>
                            <td onClick={() => router.push(`/lessons/${lesson.id}`)}>
                                {lesson.description.length > descriptionMaxLength
                                    ? lesson.description.substring(0, descriptionMaxLength) + '...'
                                    : lesson.description
                                }
                            </td>
                            <td>{lesson.accessType}</td>
                            <td><a>{lesson.author.name}</a></td>
                            <td align="center">
                                <a>
                                    <button className="btn btn-outline-success my-2 my-sm-0">Edit</button>
                                </a>
                            </td>
                            <td align="center">
                                <DeleteModal>

                                </DeleteModal>
                            </td>
                            <td align="center">
                                <button type="button" className="btn btn-outline-danger my-2 my-sm-0">Delete</button>
                            </td>
                            <td align="center">
                                <button className="btn btn-outline-info my-2 my-sm-0">Copy link</button>
                            </td>
                        </tr>
                    )
                })}
                </tbody>
            </table>
        </div>
    );
};

export default LessonsTable;
