import './App.css';

function App() {
  const cart = {
    itemId: 1,
    name: 'test product',
    price: 100,
    quantity: 3,
  };

  async function handlingCheckout() {
    const response = await fetch('http://localhost:1337/api/checkout', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(cart),
    });

    const result = await response.json();
    location.replace(result.url);
  }

  return (
    <>
      <h1>Home</h1>
      <button onClick={handlingCheckout}>Checkout</button>
    </>
  );
}

export default App;
