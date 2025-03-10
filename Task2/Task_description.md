# Description

The Stock Exchange handles the trading of company shares. The share transaction system allows investors to place orders to buy or sell shares. An order is a declaration of the intent to purchase or sell shares of a selected company. Each order must contain:

- **Order Type:** buy / sell,
- **Share Identifier:** a non-empty ASCII string consisting of characters A-Z, no longer than 5 characters,
- **Number of Shares:** a positive integer,
- **Price Limit:** a positive integer.

It is assumed that a single order can only express the intention to buy or sell shares of one company with a price limit (an upper limit in the case of a buy order, and a lower limit in the case of a sell order). A transaction occurs when there is a match between buy and sell orders for the same company, i.e., when the bid price is equal to or higher than the ask price.

For example, consider the following order sheet for the shares of a company on the stock exchange:

| Buy Side         | Sell Side        |
|------------------|------------------|
| **Order No.**    | **No. of Shares** | **Price** | **Order No.** | **Price** | **No. of Shares** |
| 4                | 100              | 125       | 1             | 123       | 10                |
| 40               | 122              |           | 2             | 124       | 25                |
| 10               | 121              |           | 3             | 125       | 30                |
| 30               | 120              |           | 126           | 20        |                   |
| 10               | 119              |           | 127           | 60        |                   |
| 20               | 118              |           |               |           |                   |

For the order sheet presented, the only buy order that can be executed is the buy order (No. 4) for 100 shares with a price limit of 125. This order will be partially executed in three transactions (in the following order): the investor will acquire 10 shares from order No. 1, 25 shares from order No. 2, and 30 shares from order No. 3. In total, the investor will acquire 65 shares. The remaining 35 shares with the limit of 125 will await execution and remain in the order sheet.

In our transaction system, time is measured in turns.[^1] The order of execution of orders is primarily determined by the price specified by the investor (a buy order with the highest limit is matched with a sell order with the lowest limit), and then the turn in which the order was placed. In the case of multiple orders placed in the same turn, the order of their submission decides. Therefore, in the example discussed, a buy order with a maximum of 125 (order No. 4) will first be matched with a sell order with a minimum of 123 (order No. 1).

When executing orders, transactions are concluded at a price equal to the price limit of the order that was submitted earlier, and in the case of orders submitted in the same turn, the submission order decides. For example, if there is an earlier sell order with an execution limit of 100 and a later buy order with a limit of 102, then by the end of the turn, a transaction will be executed and the investor will purchase shares at a price of 100 (i.e., at the execution price of the older sell order). Accordingly, in our earlier discussed example of buy order No. 4, the investor will acquire 10 shares from order No. 1 at a price of 123, 25 shares from order No. 2 at a price of 124, and 30 shares from order No. 3 at a price of 125.

Investors have several options to specify the expiration of an order:

- **Immediate Order:** The order must be executed, at least partially, in the same turn in which it is entered into the order sheet. Any unexecuted portion of the order is eliminated by the system. Several possibilities are allowed here:
  - The order cannot be executed even partially in the current turn and is eliminated,
  - The order is partially executed in the current turn and the remaining portion is eliminated,
  - The order is fully executed in the current turn and is eliminated from the system as executed.
- **Good‐Til‐Cancelled Order:** The order remains in the system until it is fully executed (through one or more transactions).
- **Fill or Kill Order:** The order must be fully executed (possibly through multiple transactions) in the same turn in which it is placed in the order sheet; if this is not possible, it is canceled.
- **Order Valid Until the End of a Specified Turn:** The order remains in the system until the end of the n-th turn, unless it is fully executed earlier.

Implement a transaction system that, in each turn, queries all investors (in a random order for each turn) about their investment decisions.

Specifically, in a single turn, investors can:
- Query the system for the current turn number,
- Ask the transaction system for the turn number and the price of the last transaction for the shares of a given company (this can be done any number of times),
- For one chosen listed company, decide to place at most one buy or sell order for its shares (if they wish to place more orders, e.g., for different companies, they must do so in subsequent turns).

After receiving the investment decisions from all investors for a turn, the transaction system executes the orders according to the rules described above.

You must implement two types of investors, each following a different investment strategy:

- **RANDOM:** An investor making random investment decisions (implement any strategy—for example, a random order type [buy/sell], random company, random number of shares, random price limit). The randomization should follow the conditions specified in the task description.
- **SMA:** An investor making decisions based on the technical analysis of individual stocks using the Simple Moving Average (SMA n) indicator—the arithmetic moving average of the prices from the last n turns (in our task, use n=5 and n=10). For the price in a given turn, consider the price of the last transaction for that company's shares; that transaction took place in one of the previous turns (if there have been no transactions for several turns, the share price remains unchanged).

  A buy signal occurs when the shorter SMA (SMA 5) crosses the longer SMA (SMA 10) from below, while a sell signal occurs when the SMA 5 crosses the SMA 10 from above. The investor begins making decisions based on this signal from the moment it is possible to compute the SMA 10 (i.e., after at least 10 turns).

Of course, there may be more types of investors; the solution should allow for their easy addition. You may also include (in addition to, not instead of, the two mentioned types) other types of investors in your solution.

To ensure efficient trading conditions on the stock exchange, it is assumed that share prices are positive natural numbers, and that the price limits in the submitted orders are positive and cannot deviate from the price of the last transaction by more than 10 units.

The transaction system ensures that when placing an order, the investor possesses the necessary assets in their portfolio (shares in the case of a sell order, and cash in the case of a buy order). Short selling (i.e., selling shares that are not physically owned) is not allowed.

If, during the execution of an order, the investor does not possess the required assets in their portfolio, the order is cancelled.

[^1]: This is a simplification for the purposes of our task.
