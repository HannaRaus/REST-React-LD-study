import React from 'react';

const Tags = ({tags}) => {
    return (
        <div>
            {tags.map(tag => {
                return (
                    <button key={tag.id} className="btn btn-outline-secondary">{tag.label}</button>
                )
            })}
        </div>
    );
};

export default Tags;