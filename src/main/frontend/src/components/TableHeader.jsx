import React from 'react';
import {Link} from "react-router-dom";

const TableHeader = ({lessons}) => {
    return (
        <h2>{
            lessons.size
                ? 'No lessons yet'
                : 'Found lessons - ' + lessons.length + ' '
        }
            <Link to="/lessons/create" className="btn btn-warning my-2 my-sm-0">Create new</Link>
        </h2>
    )
};

export default TableHeader;