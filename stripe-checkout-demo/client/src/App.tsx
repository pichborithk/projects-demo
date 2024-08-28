import './App.css';

function App() {
  const cart = {};

  async function handlingCheckOut() {
    const response = await fetch('http://localhost:1337/checkout', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ cart }),
    });
    const result = await response.json();
    console.log(result);
  }

  return (
    <>
      <h1>Home</h1>
      <button onClick={handlingCheckOut}>Checkout</button>
    </>
  );
}

export default App;
