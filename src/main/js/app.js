const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

const PageHeader = require('react-bootstrap').PageHeader;
const Panel = require('react-bootstrap').Panel;
const Table = require('react-bootstrap').Table;
const Button = require('react-bootstrap').Button;
const ButtonToolbar = require('react-bootstrap').ButtonToolbar;
const Modal = require('react-bootstrap').Modal;
const FormGroup = require('react-bootstrap').FormGroup;
const FormControl = require('react-bootstrap').FormControl;
const ControlLabel = require('react-bootstrap').ControlLabel;

class App extends React.Component {
	constructor(props) {
		super(props);
		this.state = {tasks: []};
		this.refresh = this.refresh.bind(this);
		this.addTaskToPending = this.addTaskToPending.bind(this);
		this.addTaskToWorking = this.addTaskToWorking.bind(this);
	}

	componentDidMount() {
		client({method: 'GET', path: '/v1/task'}).done(response => {
			this.setState({tasks: response.entity});
		});
	}

	refresh() {
	    this.componentDidMount();
	}

	addTaskToPending(value) {
	    var task = {value};
		client({method: 'POST', path: '/v1/task/pending', entity: task, headers: {'Content-Type': 'application/json'}}).done(response => {
			this.componentDidMount();
		});
	}

	addTaskToWorking(task) {
		client({method: 'POST', path: '/v1/task/working', entity: task, headers: {'Content-Type': 'application/json'}}).done(response => {
			this.componentDidMount();
		});
	}

	render() {
		return (
		    <div>
		        <PageHeader>Task Administrator</PageHeader>
                <Panel header="Tasks List">
                    <TaskList tasks={this.state.tasks} addTaskToWorking={this.addTaskToWorking}/>
                    <ButtonToolbar>
                        <CreateTaskDialog addTaskToPending={this.addTaskToPending}/>
                        <Button bsStyle="primary" onClick={this.refresh}>Refresh</Button>
                    </ButtonToolbar>
		        </Panel>
			</div>
		)
	}
}

class TaskList extends React.Component {
	render() {
		var tasks = this.props.tasks.map(task =>
			<Task key={task.value + '_' + task.taskState + '_' + task.prime} task={task} addTaskToWorking={this.props.addTaskToWorking}/>
		);
		return (
			<Table striped bordered condensed hover>
			    <thead>
                    <tr>
                        <th>Value to calculate</th>
                        <th>Is prime?</th>
                        <th>Task State</th>
                        <th></th>
                    </tr>
                </thead>
				<tbody>
					{tasks}
				</tbody>
			</Table>
		)
	}
}

class Task extends React.Component {
	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit() {
		this.props.addTaskToWorking(this.props.task);
	}

	render() {
	    var isPrime;
    	if (this.props.task.prime === true) {
    	    isPrime = 'YES';
    	} else if (this.props.task.prime === false){
    	    isPrime = 'NO';
    	}
		return (
			<tr>
				<td>{this.props.task.value}</td>
				<td>{isPrime}</td>
				<td>{this.props.task.taskState}</td>
				<td><Button bsStyle="primary" onClick={this.handleSubmit} disabled={this.props.task.taskState != 'PENDING_EXECUTION'}>Execute</Button></td>
			</tr>
		)
	}
}

class CreateTaskDialog extends React.Component {
    constructor(props) {
        super(props);
        this.state = { showModal: false, taskValue: '' };
        this.close = this.close.bind(this);
        this.open = this.open.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleTaskName = this.handleTaskName.bind(this);
    }

    close() {
        this.setState({ showModal: false });
    }

    open() {
        this.setState({ showModal: true });
    }

    handleSubmit() {
        this.props.addTaskToPending(this.state.taskValue);
        this.setState({ showModal: false, taskValue : '' });
    }

    handleTaskName(e) {
        this.setState({ taskValue: e.target.value });
    }

    render() {
        return (
        <div>
            <Button bsStyle="primary" onClick={this.open}>Add task</Button>
            <Modal show={this.state.showModal} onHide={this.close}>
                <Modal.Header closeButton>
                    <Modal.Title>Create new Task</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form>
                        <FormGroup>
                            <ControlLabel>Value to calculate</ControlLabel>
                            <FormControl type="number" placeholder="Value to calculate" value={this.state.taskValue} onChange={this.handleTaskName} />
                        </FormGroup>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.handleSubmit}>Submit</Button>
                </Modal.Footer>
            </Modal>
        </div>
        );
    }
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)
