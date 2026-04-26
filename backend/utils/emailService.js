const nodemailer = require('nodemailer');
const dotenv = require('dotenv');
dotenv.config();

// Create transporter
const transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: process.env.SMTP_USER,
    pass: process.env.SMTP_PASS
  }
});

// Send order confirmation email
const sendOrderConfirmation = async (email, orderId, items, totalPrice) => {
  const itemsHtml = items.map(item => `
    <tr>
      <td style="padding: 10px; border-bottom: 1px solid #ddd;">${item.productName}</td>
      <td style="padding: 10px; border-bottom: 1px solid #ddd; text-align: center;">${item.quantity}</td>
      <td style="padding: 10px; border-bottom: 1px solid #ddd; text-align: right;">₹${item.price}</td>
    </tr>
  `).join('');

  const htmlContent = `
    <!DOCTYPE html>
    <html>
    <head>
      <style>
        body { font-family: Arial, sans-serif; background-color: #f5f5f5; }
        .container { max-width: 600px; margin: 20px auto; background-color: white; padding: 20px; border-radius: 10px; }
        .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 20px; text-align: center; border-radius: 5px; }
        .content { padding: 20px; }
        table { width: 100%; border-collapse: collapse; margin: 20px 0; }
        .footer { text-align: center; color: #666; font-size: 12px; margin-top: 20px; border-top: 1px solid #ddd; padding-top: 20px; }
      </style>
    </head>
    <body>
      <div class="container">
        <div class="header">
          <h1>📺 Vijay Electronics</h1>
          <p>Order Confirmation</p>
        </div>
        
        <div class="content">
          <h2>Thank you for your order!</h2>
          <p>Your order has been successfully placed.</p>
          
          <p><strong>Order ID:</strong> ${orderId}</p>
          
          <h3>Order Details:</h3>
          <table>
            <thead>
              <tr style="background-color: #f5f5f5;">
                <th style="padding: 10px; text-align: left;">Product</th>
                <th style="padding: 10px; text-align: center;">Quantity</th>
                <th style="padding: 10px; text-align: right;">Price</th>
              </tr>
            </thead>
            <tbody>
              ${itemsHtml}
            </tbody>
          </table>
          
          <p style="font-size: 18px; color: #667eea;"><strong>Total: ₹${totalPrice}</strong></p>
          
          <p>Your order will be processed soon. You will receive tracking information via SMS.</p>
          
          <hr style="margin: 30px 0; border: none; border-top: 1px solid #ddd;">
          
          <h3>Contact Us:</h3>
          <p>📞 Phone: +91 9876898832, +91 9915649068</p>
          <p>📧 Email: navshop07@gmail.com</p>
          <p>⏰ Shop Hours: 9 AM - 9 PM Daily</p>
          
          <p style="margin-top: 30px; color: #666; font-size: 14px;">
            If you have any questions, please don't hesitate to contact us.
          </p>
        </div>
        
        <div class="footer">
          <p>&copy; 2026 Vijay Electronics. All rights reserved.</p>
          <p>This is an automated email. Please do not reply to this email.</p>
        </div>
      </div>
    </body>
    </html>
  `;

  const mailOptions = {
    from: process.env.SMTP_USER,
    to: email,
    subject: `Order Confirmation - Vijay Electronics (Order #${orderId})`,
    html: htmlContent
  };

  try {
    await transporter.sendMail(mailOptions);
    console.log(`✅ Order confirmation email sent to ${email}`);
    return true;
  } catch (error) {
    console.error('❌ Error sending email:', error);
    return false;
  }
};

// Send product update notification to admins
const sendProductUpdateNotification = async (adminEmail, productName, updateType) => {
  const htmlContent = `
    <!DOCTYPE html>
    <html>
    <head>
      <style>
        body { font-family: Arial, sans-serif; }
        .container { max-width: 600px; margin: 20px auto; background-color: #f5f5f5; padding: 20px; border-radius: 10px; }
        .alert { background-color: #e7f3ff; border-left: 4px solid #2196F3; padding: 20px; border-radius: 5px; }
      </style>
    </head>
    <body>
      <div class="container">
        <h2>📊 Product Update Notification</h2>
        
        <div class="alert">
          <p><strong>Product:</strong> ${productName}</p>
          <p><strong>Update Type:</strong> ${updateType}</p>
          <p><strong>Time:</strong> ${new Date().toLocaleString()}</p>
        </div>
        
        <p>Your product has been updated and these changes are now live on the website.</p>
        
        <p>Check your admin dashboard for more details.</p>
      </div>
    </body>
    </html>
  `;

  const mailOptions = {
    from: process.env.SMTP_USER,
    to: adminEmail,
    subject: `Product Update: ${productName}`,
    html: htmlContent
  };

  try {
    await transporter.sendMail(mailOptions);
    console.log(`✅ Update notification sent to ${adminEmail}`);
    return true;
  } catch (error) {
    console.error('❌ Error sending notification:', error);
    return false;
  }
};

module.exports = { sendOrderConfirmation, sendProductUpdateNotification };
