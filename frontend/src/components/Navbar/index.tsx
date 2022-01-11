import {ReactComponent as Githubicon} from '../../assets/img/github.svg';
import './styles.css';

function Navbar() {
    return (
        <header>
      <nav className='container'>
        <div className='dsmovie-nav-content'>
          <h1>CriptoMoedas</h1>
          <a href="https://github.com/wagnersistemalima">
            <div className='dsmovie-contact-container'>
              <Githubicon />
              <p className='dsmovie-contact-link'>/sistemalima</p>
            </div>

          </a>
        </div>
      </nav>
    </header>
    );
}

export default Navbar;